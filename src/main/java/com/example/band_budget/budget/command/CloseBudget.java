package com.example.band_budget.budget.command;

import com.example.band_budget.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CloseBudget extends Command {
    public CloseBudget(String username, Long clubId) {
        super(username, clubId);
    }
}
