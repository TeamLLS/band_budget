package com.example.band_budget.Budget;

import com.example.band_budget.budget.Budget;
import com.example.band_budget.budget.BudgetService;
import com.example.band_budget.budget.BudgetSnapshot;
import com.example.band_budget.budget.BudgetStore;
import com.example.band_budget.budget.command.CreateBudget;
import com.example.band_budget.budget.command.UpdateBudget;
import com.example.band_budget.budget.event.BudgetUpdated;
import com.example.band_budget.budget.event.BudgetEventJpo;
import com.example.band_budget.budget.form.BudgetRecord;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Import({BudgetService.class, BudgetStore.class})
public class BudgetServiceTest {


    @MockBean
    BudgetStore budgetStore;

    @Autowired
    BudgetService budgetService;

    Instant time;
    List<Budget> budgets;
    List<BudgetEventJpo> budgetEvents;
    List<BudgetSnapshot> budgetSnapshots;

    @BeforeEach
    public void saveBudgets(){
        Budget saved1 = new Budget(new CreateBudget("UserA", 1L));

        budgets = new ArrayList<>();
        budgets.add(0, saved1);


        BudgetEventJpo savedEvent1 = new BudgetEventJpo(saved1.update(new UpdateBudget("UserA", 1L, "income1", 2000)));
        BudgetEventJpo savedEvent2 = new BudgetEventJpo(saved1.update(new UpdateBudget("UserA", 1L, "expense1", -1000)));
        BudgetEventJpo savedEvent3 = new BudgetEventJpo(saved1.update(new UpdateBudget("UserA", 1L, "income2", 2000)));
        BudgetEventJpo savedEvent4 = new BudgetEventJpo(saved1.update(new UpdateBudget("UserA", 1L, "expense2", -1000)));
        BudgetEventJpo savedEvent5 = new BudgetEventJpo(saved1.update(new UpdateBudget("UserA", 1L, "expense3", -1000)));

        budgetEvents = new ArrayList<>();
        budgetEvents.add(0, savedEvent1);
        budgetEvents.add(1, savedEvent2);
        budgetEvents.add(2, savedEvent3);
        budgetEvents.add(3, savedEvent4);
        budgetEvents.add(4, savedEvent5);

        time = Instant.now().plusSeconds(60);

        Mockito.when(budgetStore.findEventListByClubId(1L, time, 0, 2))
                .thenReturn(new PageImpl<>(List.of(budgetEvents.get(0), budgetEvents.get(1)), PageRequest.of(0, 2),5));
        Mockito.when(budgetStore.findEventListByClubId(1L, time, 1, 2))
                .thenReturn(new PageImpl<>(List.of(budgetEvents.get(2), budgetEvents.get(3)), PageRequest.of(1, 2),5));
        Mockito.when(budgetStore.findEventListByClubId(1L, time, 2, 2))
                .thenReturn(new PageImpl<>(List.of(budgetEvents.get(4)), PageRequest.of(2, 2),5));
    }


    @Test
    public void getRecordListTest(){
        List<BudgetRecord> listA = budgetService.getRecords(1L, time, 0, 2);
        List<BudgetRecord> listB = budgetService.getRecords(1L, time, 1, 2);
        List<BudgetRecord> listC = budgetService.getRecords(1L, time, 2, 2);

        Assertions.assertThat(listA.size()).isEqualTo(2);
        Assertions.assertThat(listB.size()).isEqualTo(2);
        Assertions.assertThat(listC.size()).isEqualTo(1);

        BudgetRecord record = new BudgetRecord((BudgetUpdated) budgetEvents.get(4).toEvent());

        Assertions.assertThat(listC.get(0))
                .hasFieldOrPropertyWithValue("clubId", record.getClubId())
                .hasFieldOrPropertyWithValue("description", record.getDescription())
                .hasFieldOrPropertyWithValue("amount", record.getAmount())
                .hasFieldOrPropertyWithValue("username", record.getUsername())
                .hasFieldOrPropertyWithValue("time", record.getTime());
    }
}
