package com.example.band_budget.PayBook.command;

import com.example.band_budget.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ClosePayBook extends Command {

    @NotNull
    private Long payBookId;

    public ClosePayBook(String username, Long payBookId, Long clubId) {
        super(username, clubId);
        this.payBookId = payBookId;
    }
}
