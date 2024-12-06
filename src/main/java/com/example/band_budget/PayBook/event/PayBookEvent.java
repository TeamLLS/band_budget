package com.example.band_budget.PayBook.event;

import com.example.band_budget.core.Event;
import com.example.band_budget.external.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public abstract class PayBookEvent extends Event {

    private Long payBookId;

    public PayBookEvent(String eventId, Long payBookId, Long clubId, String triggeredBy, Instant time) {
        super(eventId, clubId, triggeredBy, time);
        this.payBookId = payBookId;
    }
}
