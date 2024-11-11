package com.example.band_budget.PayBook.command;

import com.example.band_budget.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CancelPayBook extends Command {

    @NotNull
    private Long payBookId;
    @NotNull
    private Long clubId;

    public CancelPayBook(String username, Long payBookId, Long clubId) {
        super(username);
        this.payBookId = payBookId;
        this.clubId = clubId;
    }
}
