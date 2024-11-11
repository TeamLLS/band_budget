package com.example.band_budget.PayMember.event;

import com.example.band_budget.PayMember.PayMember;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class PayMemberCreated extends PayMemberEvent{

    private String username;
    private String memberName;


    public PayMemberCreated(String username, PayMember payMember) {
        super(UUID.randomUUID().toString(), payMember.getPayBook().getId(), payMember.getMemberId(), username, Instant.now());
        this.username = payMember.getUsername();
        this.memberName = payMember.getMemberName();
    }
}
