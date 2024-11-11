package com.example.band_budget.budget.form;


import com.example.band_budget.budget.event.BudgetUpdated;
import com.example.band_budget.budget.event.BudgetEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class BudgetRecord {

    private Long clubId;
    private String description;
    private Integer amount;
    private String username;
    private Instant time;

/*
    public BudgetRecord(BudgetIncreased event) {
        this.clubId = event.getClubId();
        this.description = event.getDescription();
        this.amount = event.getAmount();
        this.username = event.getTriggeredBy();
        this.time = event.getTime();
    }

    public BudgetRecord(BudgetDecreased event) {
        this.clubId = event.getClubId();
        this.description = event.getDescription();
        this.amount = event.getAmount();
        this.username = event.getTriggeredBy();
        this.time = event.getTime();
    }

    public BudgetRecord(BudgetCreated event){
        this.clubId = event.getClubId();
        this.description ="created";
        this.amount = 0;
        this.username = event.getTriggeredBy();
        this.time = event.getTime();
    }*/

    public BudgetRecord(BudgetEvent event){
        this.clubId = event.getClubId();
        this.username = event.getTriggeredBy();
        this.time = event.getTime();

        if(event instanceof BudgetUpdated){
            this.description = ((BudgetUpdated)event).getDescription();
            this.amount = ((BudgetUpdated)event).getAmount();

        }else{
            this.description ="created";
            this.amount = 0;
        }
    }
}
