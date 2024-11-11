package com.example.band_budget.budget;

import com.example.band_budget.budget.command.CloseBudget;
import com.example.band_budget.budget.command.CreateBudget;
import com.example.band_budget.budget.command.UpdateBudget;
import com.example.band_budget.budget.event.*;
import com.example.band_budget.budget.form.BudgetDto;
import com.example.band_budget.budget.form.BudgetRecord;
import com.example.band_budget.budget.policy.BudgetStatusAccessPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetStore budgetStore;

    //Club 생성시 자동
    public Long createBudget(CreateBudget command){
        return budgetStore.save(command.getUsername(), new Budget(command)).getId();
    }

    public Long updateBudget(UpdateBudget command){
        Budget budget = budgetStore.findByClubId(command.getClubId());
        BudgetStatusAccessPolicy.isActive(budget);

        BudgetUpdated event = budget.update(command);
        budgetStore.saveEvent(event);

        if((budgetStore.countEventByClubId(command.getClubId())-1)%5==0){
            budgetStore.saveSnapshot(budget.snapshot(event.getTime()));
        }

        return budget.getId();
    }

    public Long closeBudget(CloseBudget command){
        Budget budget = budgetStore.findByClubId(command.getClubId());
        BudgetStatusAccessPolicy.isActive(budget);

        budgetStore.saveEvent(budget.close(command));
        return budget.getId();
    }

    public BudgetDto getBudgetInfo(Long clubId, Instant time){

        if(time==null){
            return new BudgetDto(budgetStore.findByClubId(clubId));
        }

        return new BudgetDto(budgetStore.findByClubIdAndTime(clubId, time));

    }

    public List<BudgetRecord> getRecords(Long clubId, Instant time, int pageNo, int pageSize){

        if(time==null){
            time = Instant.now();
        }

        List<BudgetEventJpo> list = budgetStore.findEventListByClubId(clubId, time, pageNo, pageSize).getContent();
        List<BudgetRecord> result = new ArrayList<>();

        for (BudgetEventJpo eventJpo : list) {
            result.add(new BudgetRecord(eventJpo.toEvent()));
        }

        return result;
    }
}
