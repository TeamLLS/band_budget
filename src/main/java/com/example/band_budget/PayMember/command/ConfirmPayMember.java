package com.example.band_budget.PayMember.command;

import com.example.band_budget.PayMember.PayStatus;
import com.example.band_budget.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ConfirmPayMember extends Command {

    @NotNull
    private Long payBookId;


    public ConfirmPayMember(String username, Long payBookId) {
        super(username, null);
        this.payBookId = payBookId;
    }
}
