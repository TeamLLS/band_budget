package com.example.band_budget.PayMember.form;

import com.example.band_budget.PayMember.PayMember;
import com.example.band_budget.PayMember.PayStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PayBookRecord {
    private Long payBookId;
    private String name;
    private Integer amount;
    private String payStatus;
    private Instant time;

    public PayBookRecord(PayMember payMember) {
        this.payBookId = payMember.getPayBook().getId();
        this.name = payMember.getPayBook().getName();
        this.amount = payMember.getPayBook().getAmount();
        this.payStatus = payMember.getStatus().getDisplay();
        this.time = payMember.getPayBook().getCreatedAt();
    }
}
