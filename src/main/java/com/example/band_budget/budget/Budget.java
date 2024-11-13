package com.example.band_budget.budget;

import com.example.band_budget.budget.command.CloseBudget;
import com.example.band_budget.budget.command.CreateBudget;
import com.example.band_budget.budget.command.UpdateBudget;
import com.example.band_budget.budget.event.BudgetClosed;
import com.example.band_budget.budget.event.BudgetUpdated;
import com.example.band_budget.budget.event.BudgetEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Budget {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Long clubId;
    private Integer amount;
    private Instant createdAt;
    private Instant expiredAt;

    public Budget(CreateBudget command){
        this.clubId = command.getClubId();
        this.amount = 0;
        this.createdAt = Instant.now();
    }

    public BudgetSnapshot snapshot(Instant time){
        return new BudgetSnapshot(this, time);
    }

    public BudgetUpdated update(UpdateBudget command){
        this.amount += command.getAmount();
        return new BudgetUpdated(command);
    }

    public void restore(BudgetEvent event){
        if(event instanceof BudgetUpdated){
            this.amount += ((BudgetUpdated) event).getAmount();
        }
    }

    public BudgetClosed close(CloseBudget command){
        this.expiredAt = Instant.now();
        return new BudgetClosed(command.getUsername(), this);
    }
}
