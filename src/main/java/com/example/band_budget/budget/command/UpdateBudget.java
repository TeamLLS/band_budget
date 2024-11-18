package com.example.band_budget.budget.command;

import com.example.band_budget.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateBudget extends Command {
    @NotNull
    private String description;

    @NotNull
    private Integer amount;

    public UpdateBudget(String username, Long clubId, String description, Integer amount) {
        super(username, clubId);
        this.description = description;
        this.amount = amount;
    }
}
