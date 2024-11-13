package com.example.band_budget.Budget.payBook;


import com.example.band_budget.PayBook.PayBook;
import com.example.band_budget.PayBook.PayBookStore;
import com.example.band_budget.PayBook.command.CancelPayBook;
import com.example.band_budget.PayBook.command.ClosePayBook;
import com.example.band_budget.PayBook.command.CreatePayBook;
import com.example.band_budget.PayBook.event.PayBookEventJpo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@Import(PayBookStore.class)
public class PayBookStoreTest {

    @Autowired
    PayBookStore payBookStore;


    List<PayBook> payBooks;
    List<PayBookEventJpo> payBookEvents;

    @BeforeEach
    public void savePayBooks(){
        PayBook saved1 = new PayBook(new CreatePayBook("TestManager", 1L, 2000, "TestBookA", "for TestA"));
        PayBook saved2 = new PayBook(new CreatePayBook("TestManager", 1L, 1500, "TestBookB", "for TestB"));
        PayBook saved3 = new PayBook(new CreatePayBook("TestManager", 1L, 3000, "TestBookC", "for TestC"));

        payBookStore.save("TestManager", saved1);
        payBookStore.save("TestManager", saved2);
        payBookStore.save("TestManager", saved3);

        payBooks = new ArrayList<>();

        payBooks.add(0, saved1);
        payBooks.add(1, saved2);
        payBooks.add(2, saved3);

        PayBookEventJpo savedEvent1 = payBookStore.saveEvent(saved2.cancel(new CancelPayBook("TestManager", saved2.getId(), 1L)));
        PayBookEventJpo savedEvent2 = payBookStore.saveEvent(saved3.close(new ClosePayBook("TestManager", saved3.getId(), 1L)));

        payBookEvents = new ArrayList<>();

        payBookEvents.add(0, savedEvent1);
        payBookEvents.add(1, savedEvent2);
    }

    @Test
    public void findTest(){
        PayBook payBook = payBooks.get(0);

        PayBook find = payBookStore.find(payBook.getId());

        Assertions.assertThat(find).isEqualTo(payBook);
    }


    @Test
    public void findListTest(){
        List<PayBook> listA = payBookStore.findListByClubId(1L, 0, 2).getContent();
        List<PayBook> listB = payBookStore.findListByClubId(1L, 1, 2).getContent();

        Assertions.assertThat(listA.size()).isEqualTo(2);
        Assertions.assertThat(listB.size()).isEqualTo(1);
        Assertions.assertThat(listB).contains(payBooks.get(2));
    }

    @Test
    public void findEventTest(){

        PayBookEventJpo event = payBookEvents.get(0);

        PayBookEventJpo find = payBookStore.findEvent(event.getEventId());

        Assertions.assertThat(find).isEqualTo(event);
    }
}
