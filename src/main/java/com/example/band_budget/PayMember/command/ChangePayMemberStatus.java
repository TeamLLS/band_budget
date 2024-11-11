package com.example.band_budget.PayMember.command;

import com.example.band_budget.PayBook.PayBook;
import com.example.band_budget.PayMember.PayStatus;
import com.example.band_budget.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

@Getter
@NoArgsConstructor
public class ChangePayMemberStatus extends Command {

    @NotNull
    private Long payBookId;
    @NotNull
    private Long memberId;
    @NotNull
    private PayStatus status;


    public ChangePayMemberStatus(String username, Long payBookId, Long memberId, PayStatus status) {
        super(username);
        this.payBookId = payBookId;
        this.memberId = memberId;
        this.status = status;
    }

}
