package com.example.band_budget.PayBook.event;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Entity
@Getter
@NoArgsConstructor
public class PayBookEventJpo {

    @Id
    private String eventId;
    private Long payBookId;
    private String triggeredBy;
    private String eventType;
    @Lob
    private String payload;
    private Instant time;


    public PayBookEventJpo(PayBookEvent event) {
        this.eventId = event.getEventId();
        this.payBookId = event.getPayBookId();
        this.triggeredBy = event.getTriggeredBy();
        this.eventType = event.typeName();
        this.payload = event.Payload();
        this.time = event.getTime();
    }


}
