package com.example.band_budget.PayMember.form;

import com.example.band_budget.PayMember.PayMember;
import com.example.band_budget.PayMember.PayStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class PayRecord {

    private Long id;
    private Long payBookId;
    private String username;
    private Long memberId;
    private String memberName;
    private Instant paidAt;
    private String status;

    public PayRecord(PayMember payMember) {
        this.id = payMember.getId();
        this.payBookId = payMember.getPayBook().getId();
        this.username = payMember.getUsername();
        this.memberId = payMember.getMemberId();
        this.memberName = payMember.getMemberName();
        this.paidAt = payMember.getPaidAt();
        this.status = payMember.getStatus().getDisplay();
    }
}
