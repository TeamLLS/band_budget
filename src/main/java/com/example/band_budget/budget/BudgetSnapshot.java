package com.example.band_budget.budget;

import com.example.band_budget.external.JsonUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
public class BudgetSnapshot {

    @Id
    @GeneratedValue
    private Long id;

    private Long clubId;

    @Lob
    private String payload;

    private Instant time;

    public BudgetSnapshot(Budget budget){
        this.clubId = budget.getClubId();
        this.payload= JsonUtil.toJson(budget);
        this.time=Instant.now();
    }

    public Budget toBudget(){
        return JsonUtil.fromJson(this.payload, Budget.class);
    }
}
