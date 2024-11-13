package com.example.band_budget.Budget.payMember;


import com.example.band_budget.PayBook.PayBook;
import com.example.band_budget.PayBook.PayBookStore;
import com.example.band_budget.PayBook.command.CreatePayBook;
import com.example.band_budget.PayMember.PayMember;
import com.example.band_budget.PayMember.PayMemberService;
import com.example.band_budget.PayMember.PayMemberStore;
import com.example.band_budget.PayMember.PayStatus;
import com.example.band_budget.PayMember.command.ChangePayMemberStatus;
import com.example.band_budget.PayMember.command.ConfirmPayMember;
import com.example.band_budget.PayMember.command.RegisterPayMember;
import com.example.band_budget.PayMember.event.PayMemberEventJpo;
import com.example.band_budget.PayMember.form.PayBookRecord;
import com.example.band_budget.PayMember.form.PayRecord;
import com.example.band_budget.budget.BudgetService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Import({PayMemberService.class, PayMemberStore.class, PayBookStore.class, BudgetService.class})
public class PayMemberServiceTest {
    @MockBean
    PayMemberStore payMemberStore;

    @MockBean
    PayBookStore payBookStore;

    @MockBean
    BudgetService budgetService;

    @Autowired
    PayMemberService payMemberService;

    List<PayMember> payMembers;

    List<PayMemberEventJpo> payMemberEvents;

    PayBook payBook;

    @BeforeEach
    public void savePayMembers(){
        payBook = new PayBook(new CreatePayBook("TestManager", 1L, 11000, "November", "for dues1"));

        RegisterPayMember command1 = new RegisterPayMember("TestManager", 1L, 1L, 1L, "MemberA", "UserA");
        RegisterPayMember command2 = new RegisterPayMember("TestManager", 1L, 1L, 2L, "MemberB", "UserB");
        RegisterPayMember command3 = new RegisterPayMember("TestManager", 1L, 1L, 3L, "MemberC", "UserC");

        command1.setPayBook(payBook);
        command2.setPayBook(payBook);
        command3.setPayBook(payBook);

        PayMember saved1 = new PayMember(command1);
        PayMember saved2 = new PayMember(command2);
        PayMember saved3 = new PayMember(command3);

        payMembers = new ArrayList<>();
        payMembers.add(0, saved1);
        payMembers.add(1, saved2);
        payMembers.add(2, saved3);

        PayMemberEventJpo savedEvent1 = new PayMemberEventJpo(saved1.update(new ChangePayMemberStatus("TestManager", 1L, 1L, PayStatus.PAID)));
        PayMemberEventJpo savedEvent2 = new PayMemberEventJpo(saved2.update(new ChangePayMemberStatus("TestManager", 1L, 2L, PayStatus.EXCLUDED)));
        PayMemberEventJpo savedEvent3 = new PayMemberEventJpo(saved3.update(new ChangePayMemberStatus("TestManager", 1L, 2L, PayStatus.LATE_PAID)));

        payMemberEvents = new ArrayList<>();
        payMemberEvents.add(0, savedEvent1);
        payMemberEvents.add(1, savedEvent2);
        payMemberEvents.add(2, savedEvent3);

        Mockito.when(payMemberStore.find(1L, 1L)).thenReturn(payMembers.get(0));
        Mockito.when(payMemberStore.find(1L, 2L)).thenReturn(payMembers.get(1));
        Mockito.when(payMemberStore.find(1L, 3L)).thenReturn(payMembers.get(2));

        Mockito.when(payMemberStore.findListWithPayBookByUsername(1L, "UserA", 0, 2))
                .thenReturn(new PageImpl<>(List.of(payMembers.get(0)), PageRequest.of(0, 2),1));

        Mockito.when(payMemberStore.findListByPayBookId(1L, 0, 2))
                .thenReturn(new PageImpl<>(List.of(payMembers.get(0), payMembers.get(1)), PageRequest.of(0, 2),3));
        Mockito.when(payMemberStore.findListByPayBookId(1L, 1, 2))
                .thenReturn(new PageImpl<>(List.of(payMembers.get(2)), PageRequest.of(1, 2),3));

    }

    @Test
    public void getBookListTest(){
        List<PayBookRecord> list = payMemberService.getPayBookRecordList(1L, "UserA", 0, 2);

        PayBookRecord record = new PayBookRecord(payMembers.get(0));

        Assertions.assertThat(list.size()).isEqualTo(1);
        Assertions.assertThat(list.get(0))
                .hasFieldOrPropertyWithValue("payBookId", record.getPayBookId())
                .hasFieldOrPropertyWithValue("name", record.getName())
                .hasFieldOrPropertyWithValue("amount", record.getAmount())
                .hasFieldOrPropertyWithValue("payStatus", record.getStatus())
                .hasFieldOrPropertyWithValue("time", record.getTime());
    }

    @Test
    public void getListTest(){
        List<PayRecord> listA = payMemberService.getPayRecordList(1L, 0, 2);
        List<PayRecord> listB = payMemberService.getPayRecordList(1L, 1, 2);

        PayRecord record = new PayRecord(payMembers.get(2));

        Assertions.assertThat(listA.size()).isEqualTo(2);
        Assertions.assertThat(listB.size()).isEqualTo(1);
        Assertions.assertThat(listB.get(0))
                .hasFieldOrPropertyWithValue("id", record.getId())
                .hasFieldOrPropertyWithValue("payBookId", record.getPayBookId())
                .hasFieldOrPropertyWithValue("username", record.getUsername())
                .hasFieldOrPropertyWithValue("memberId", record.getMemberId())
                .hasFieldOrPropertyWithValue("memberName", record.getMemberName())
                .hasFieldOrPropertyWithValue("status", record.getStatus());
    }

    @Test
    public void changeStatusTest(){
        List<PayRecord> listA = payMemberService.getPayRecordList(1L, 0, 2);
        PayRecord recordA = listA.get(0);
        Assertions.assertThat(recordA.getStatus()).isEqualTo(PayStatus.PAID.getDisplay());

        payMemberService.changePayMemberStatus(new ChangePayMemberStatus("TestManager", 1L, 1L, PayStatus.UNPAID));

        List<PayRecord> listB = payMemberService.getPayRecordList(1L, 0, 2);
        PayRecord recordB = listB.get(0);
        Assertions.assertThat(recordB.getStatus()).isEqualTo(PayStatus.UNPAID.getDisplay());

    }

    @Test
    public void confirmTest(){
        Integer confirmed = payMemberService.confirmPayMember(new ConfirmPayMember("TestManager", 1L));
        Assertions.assertThat(confirmed).isEqualTo(3);
    }

}
