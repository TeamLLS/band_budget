package com.example.band_budget.budget.event;

import com.example.band_budget.external.JsonUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@NoArgsConstructor
public class BudgetEventJpo {

    @Id
    private String eventId;

    private Long clubId;

    private String triggeredBy;
    private String eventType;
    @Lob
    private String payload;
    private Instant time;

    public BudgetEventJpo(BudgetEvent budgetEvent) {
        this.eventId = budgetEvent.getEventId();
        this.clubId = budgetEvent.getClubId();
        this.triggeredBy = budgetEvent.getTriggeredBy();
        this.eventType = budgetEvent.typeName();
        this.payload = budgetEvent.Payload();
        this.time = budgetEvent.getTime();
    }

    public BudgetEvent toEvent() {
        try {
            return (BudgetEvent) JsonUtil.fromJson(this.payload, Class.forName(this.eventType));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
