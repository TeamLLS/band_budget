package com.example.band_budget.budget.event;

import com.example.band_budget.budget.command.UpdateBudget;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class BudgetUpdated extends BudgetEvent{

    @NotNull
    private String description;
    @NotNull
    private Integer amount;

    public BudgetUpdated(UpdateBudget command) {
        super(UUID.randomUUID().toString(), command.getClubId(), command.getUsername(), Instant.now());
        this.description = command.getDescription();
        this.amount = command.getAmount();
    }
}
