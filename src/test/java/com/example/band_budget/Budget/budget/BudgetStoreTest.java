package com.example.band_budget.Budget.budget;

import com.example.band_budget.budget.Budget;
import com.example.band_budget.budget.BudgetSnapshot;
import com.example.band_budget.budget.BudgetStore;
import com.example.band_budget.budget.command.CreateBudget;
import com.example.band_budget.budget.command.UpdateBudget;
import com.example.band_budget.budget.event.BudgetUpdated;
import com.example.band_budget.budget.event.BudgetEventJpo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@Import(BudgetStore.class)
public class BudgetStoreTest {
    @Autowired
    BudgetStore budgetStore;

    List<Instant> times;
    List<Budget> budgets;
    List<BudgetEventJpo> budgetEvents;
    List<BudgetSnapshot> budgetSnapshots;


    @BeforeEach
    public void saveBudgets() throws InterruptedException {
        Budget saved1 = new Budget(new CreateBudget("TestManagerA", 1L));
        Budget saved2 = new Budget(new CreateBudget("TestManagerB", 2L));
        Budget saved3 = new Budget(new CreateBudget("TestManagerC", 3L));

        budgetStore.save("TestManagerA", saved1);
        budgetStore.save("TestManagerB", saved2);
        budgetStore.save("TestManagerC", saved3);

        budgets = new ArrayList<>();
        budgets.add(0, saved1);
        budgets.add(1, saved2);
        budgets.add(2, saved3);


        times = new ArrayList<>();

        BudgetEventJpo savedEvent1 = budgetStore.saveEvent(saved2.update(new UpdateBudget("TestManagerB", 2L, "income", 3000)));
        times.add(0, Instant.now());
        Thread.sleep(1000L);

        BudgetEventJpo savedEvent2 = budgetStore.saveEvent(saved2.update(new UpdateBudget("TestManagerB", 2L, "expense", -2000)));
        times.add(1, Instant.now());
        Thread.sleep(1000L);

        BudgetEventJpo savedEvent3 = budgetStore.saveEvent(saved2.update(new UpdateBudget("TestManagerB", 2L, "income", 10000)));
        BudgetSnapshot savedSnapshot = saved2.snapshot(Instant.now());

        budgetStore.saveSnapshot(savedSnapshot);

        budgetEvents = new ArrayList<>();
        budgetEvents.add(0, savedEvent1);
        budgetEvents.add(1, savedEvent2);
        budgetEvents.add(2, savedEvent3);

        budgetSnapshots = new ArrayList<>();
        budgetSnapshots.add(0, savedSnapshot);


        times.add(2, Instant.now());
    }


    @Test
    public void findTest(){
        Budget saved = budgets.get(0);
        Budget find = budgetStore.findByClubId(saved.getClubId());

        Assertions.assertThat(saved).isEqualTo(find);
    }

    @Test
    public void findTestWithTime(){
        BudgetSnapshot snapshot = budgetSnapshots.get(0);

        Budget time1 = budgetStore.findByClubIdAndTime(2L, times.get(0));
        Budget time2 = budgetStore.findByClubIdAndTime(2L, times.get(1));
        Budget time3 = budgetStore.findByClubIdAndTime(2L, snapshot.getTime());

        Assertions.assertThat(time1.getAmount()).isEqualTo(3000);
        Assertions.assertThat(time2.getAmount()).isEqualTo(1000);
        Assertions.assertThat(time3.getAmount()).isEqualTo(11000);
    }

    @Test
    public void countEventTest(){
        Assertions.assertThat(budgetStore.countEventByClubId(2L)).isEqualTo(4);
    }

    @Test
    public void findEventListTest(){
        Page<BudgetEventJpo> listA = budgetStore.findEventListByClubId(2L, times.get(2), 0, 2);
        Page<BudgetEventJpo> listB = budgetStore.findEventListByClubId(2L, times.get(2), 1, 2);

        Assertions.assertThat(listA.getSize()).isEqualTo(2);
        Assertions.assertThat(listB.getSize()).isEqualTo(2);
        Assertions.assertThat(listB).contains(budgetEvents.get(1));
        Assertions.assertThat(listB).contains(budgetEvents.get(2));
    }
}
