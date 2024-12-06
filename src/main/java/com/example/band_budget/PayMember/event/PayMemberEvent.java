package com.example.band_budget.PayMember.event;

import com.example.band_budget.core.Event;
import com.example.band_budget.external.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public abstract class PayMemberEvent extends Event {


    private Long payBookId;
    private Long memberId;

    public PayMemberEvent(String eventId, Long clubId, Long payBookId, Long memberId, String triggeredBy, Instant time) {
        super(eventId, clubId, triggeredBy, time);
        this.payBookId = payBookId;
        this.memberId = memberId;
    }
}
