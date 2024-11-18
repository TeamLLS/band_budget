package com.example.band_budget.Budget.payMember;


import com.example.band_budget.PayBook.PayBook;
import com.example.band_budget.PayBook.PayBookStore;
import com.example.band_budget.PayBook.command.CreatePayBook;
import com.example.band_budget.PayBook.event.PayBookEventJpo;
import com.example.band_budget.PayMember.PayMember;
import com.example.band_budget.PayMember.PayMemberStore;
import com.example.band_budget.PayMember.PayStatus;
import com.example.band_budget.PayMember.command.ChangePayMemberStatus;
import com.example.band_budget.PayMember.command.ConfirmPayMember;
import com.example.band_budget.PayMember.command.RegisterPayMember;
import com.example.band_budget.PayMember.event.PayMemberEventJpo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@Import({PayBookStore.class, PayMemberStore.class})
public class PayMemberStoreTest {


    @Autowired
    PayMemberStore payMemberStore;

    @Autowired
    PayBookStore payBookStore;


    PayBook payBook;
    List<PayMember> payMembers;
    List<PayMemberEventJpo> payMemberEvents;


    @BeforeEach
    public void savePayMembers(){

        payBook = payBookStore.save("TestManager", new PayBook(new CreatePayBook("TestManager", 1L, 2000, "TestBookA", "for TestA")));


        RegisterPayMember commandA = new RegisterPayMember("TestManager", payBook.getId(), payBook.getClubId(), 1L, "MemberA", "UserA");
        RegisterPayMember commandB = new RegisterPayMember("TestManager", payBook.getId(), payBook.getClubId(), 2L, "MemberB", "UserB");
        RegisterPayMember commandC = new RegisterPayMember("TestManager", payBook.getId(), payBook.getClubId(), 3L, "MemberC", "UserC");
        RegisterPayMember commandD = new RegisterPayMember("TestManager", payBook.getId(), payBook.getClubId(), 4L, "MemberD", "UserD");

        commandA.setPayBook(payBook);
        commandB.setPayBook(payBook);
        commandC.setPayBook(payBook);
        commandD.setPayBook(payBook);

        PayMember memberA = new PayMember(commandA);
        PayMember memberB = new PayMember(commandB);
        PayMember memberC = new PayMember(commandC);
        PayMember memberD = new PayMember(commandD);

        payMemberStore.save("TestManager", memberA);
        payMemberStore.save("TestManager", memberB);
        payMemberStore.save("TestManager", memberC);
        payMemberStore.save("TestManager", memberD);

        payMembers = new ArrayList<>();

        payMembers.add(0, memberA);
        payMembers.add(1, memberB);
        payMembers.add(2, memberC);
        payMembers.add(3, memberD);

        PayMemberEventJpo savedEvent1 = payMemberStore.saveEvent(memberA.update(new ChangePayMemberStatus("TestManager", payBook.getId(), memberA.getMemberId(), PayStatus.PAID)));
        PayMemberEventJpo savedEvent2 = payMemberStore.saveEvent(memberA.confirm(new ConfirmPayMember("TestManager", payBook.getId())));
        PayMemberEventJpo savedEvent3 = payMemberStore.saveEvent(memberB.update(new ChangePayMemberStatus("TestManager", payBook.getId(), memberB.getMemberId(), PayStatus.LATE_PAID)));
        PayMemberEventJpo savedEvent4 = payMemberStore.saveEvent(memberB.confirm(new ConfirmPayMember("TestManager", payBook.getId())));
        PayMemberEventJpo savedEvent5 = payMemberStore.saveEvent(memberD.update(new ChangePayMemberStatus("TestManager", payBook.getId(), memberD.getMemberId(), PayStatus.EXCLUDED)));

        payMemberEvents = new ArrayList<>();

        payMemberEvents.add(0, savedEvent1);
        payMemberEvents.add(1, savedEvent2);
        payMemberEvents.add(2, savedEvent3);
        payMemberEvents.add(3, savedEvent4);
        payMemberEvents.add(4, savedEvent5);
    }

    @Test
    public void findTest(){

        PayMember payMember = payMembers.get(0);

        PayMember find = payMemberStore.find(payBook.getId(), payMember.getMemberId());

        Assertions.assertThat(find).isEqualTo(payMember);
    }

    @Test
    public void findListTest(){

        List<PayMember> listA = payMemberStore.findListByPayBookId(payBook.getId(), 0, 2).getContent();
        List<PayMember> listB = payMemberStore.findListByPayBookId(payBook.getId(), 1, 2).getContent();

        Assertions.assertThat(listA.size()).isEqualTo(2);
        Assertions.assertThat(listB.size()).isEqualTo(1);
        Assertions.assertThat(listB).contains(payMembers.get(2));
    }

    @Test
    public void findBookTest(){
        List<PayMember> listA = payMemberStore.findListWithPayBookByUsername(1L, "UserA", 0, 1).getContent();

        Assertions.assertThat(listA.size()).isEqualTo(1);
        Assertions.assertThat(listA).contains(payMembers.get(0));
    }
    
    @Test
    public void findEventTest(){

        PayMemberEventJpo event = payMemberEvents.get(0);

        PayMemberEventJpo find = payMemberStore.findEvent(event.getEventId());

        Assertions.assertThat(find).isEqualTo(event);
    }
}
