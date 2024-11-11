package com.example.band_budget.PayMember.event;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
public class PayMemberEventJpo {

    @Id
    private String eventId;
    private Long payBookId;
    private Long memberId;
    private String triggeredBy;
    private String eventType;
    @Lob
    private String payload;
    private Instant time;

    public PayMemberEventJpo(PayMemberEvent event) {
        this.eventId = event.getEventId();
        this.payBookId = event.getPayBookId();
        this.memberId = event.getMemberId();
        this.triggeredBy = event.getEventId();
        this.eventType = event.typeName();
        this.payload = event.Payload();
        this.time = event.getTime();
    }
}
