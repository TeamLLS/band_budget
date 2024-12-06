package com.example.band_budget.PayMember.command;

import com.example.band_budget.PayMember.PayStatus;
import com.example.band_budget.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class UpdatePayMember extends Command {

    @NotNull
    private Long payBookId;
    @NotNull
    private Long memberId;
    @NotNull
    private PayStatus status;
    private Instant time;

    public UpdatePayMember(String username, Long payBookId, Long memberId, PayStatus status, Instant time) {
        super(username, null);
        this.payBookId = payBookId;
        this.memberId = memberId;
        this.status = status;
        this.time = time;
    }

}
