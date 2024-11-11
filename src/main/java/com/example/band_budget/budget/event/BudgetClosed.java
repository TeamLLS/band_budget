package com.example.band_budget.budget.event;

import com.example.band_budget.budget.Budget;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@NoArgsConstructor
public class BudgetClosed extends BudgetEvent{

    public BudgetClosed(String username, Budget budget) {
        super(UUID.randomUUID().toString(), budget.getClubId(), username, budget.getExpiredAt());
    }
}
