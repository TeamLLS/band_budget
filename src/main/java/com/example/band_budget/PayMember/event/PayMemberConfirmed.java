package com.example.band_budget.PayMember.event;

import com.example.band_budget.PayMember.PayMember;
import com.example.band_budget.PayMember.PayStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;


@Getter
@NoArgsConstructor
public class PayMemberConfirmed extends PayMemberEvent{

    private Integer amount;
    private Instant paidAt;
    private PayStatus status;

    public PayMemberConfirmed(String username, PayMember payMember) {
        super(UUID.randomUUID().toString(), payMember.getPayBook().getClubId(), payMember.getPayBook().getId(),
                payMember.getMemberId(), username, Instant.now());
        this.status = payMember.getStatus();
        this.paidAt = payMember.getPaidAt();
        this.amount = payMember.getPayBook().getAmount();
    }
}
