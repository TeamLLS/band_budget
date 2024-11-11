package com.example.band_budget.PayBook.event;

import com.example.band_budget.external.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public abstract class PayBookEvent {

    private String eventId;
    private Long payBookId;
    private Long clubId;
    private String triggeredBy;
    private Instant time;

    public PayBookEvent(String eventId, Long payBookId, Long clubId, String triggeredBy, Instant time) {
        this.eventId = eventId;
        this.payBookId = payBookId;
        this.clubId = clubId;
        this.triggeredBy = triggeredBy;
        this.time = time;
    }


    public String typeName(){
        return this.getClass().getTypeName();
    }

    public String Payload(){
        return JsonUtil.toJson(this);
    }
}
