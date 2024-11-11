package com.example.band_budget.budget.event;

import com.example.band_budget.budget.Budget;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class BudgetCreated extends BudgetEvent{

    public BudgetCreated(String username, Budget budget) {
        super(UUID.randomUUID().toString(), budget.getClubId(), username, budget.getCreatedAt());
    }
}
