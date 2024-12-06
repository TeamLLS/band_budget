package com.example.band_budget.budget.event;

import com.example.band_budget.core.Event;
import com.example.band_budget.external.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public abstract class BudgetEvent extends Event {

    public BudgetEvent(String eventId, Long clubId, String triggeredBy, Instant time) {
        super(eventId, clubId, triggeredBy, time);
    }
}
