package com.example.band_budget.PayBook.command;

import com.example.band_budget.budget.Budget;
import com.example.band_budget.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePayBook extends Command {

    private Integer amount;

    private String name;

    private String description;

    public CreatePayBook(String username, Long clubId, Integer amount, String name, String description) {
        super(username, clubId);
        this.name = name;
        this.description = description;
        this.amount = amount;
    }
}
