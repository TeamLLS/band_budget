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

    private PayStatus status;

    public PayMemberConfirmed(String username, PayMember payMember) {
        super(UUID.randomUUID().toString(), payMember.getPayBook().getId(), payMember.getMemberId(), username, Instant.now());
        this.status = payMember.getStatus();
    }
}
