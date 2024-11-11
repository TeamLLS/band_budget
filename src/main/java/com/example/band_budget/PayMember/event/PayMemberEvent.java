package com.example.band_budget.PayMember.event;

import com.example.band_budget.external.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public abstract class PayMemberEvent {

    private String eventId;
    private Long payBookId;
    private Long memberId;
    private String triggeredBy;
    private Instant time;


    public PayMemberEvent(String eventId, Long payBookId, Long memberId, String triggeredBy, Instant time) {
        this.eventId = eventId;
        this.payBookId = payBookId;
        this.memberId = memberId;
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
