package com.example.band_budget.budget.form;

import com.example.band_budget.budget.Budget;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BudgetDto {

    private Long id;
    private Long clubId;
    private Integer amount;
    private Boolean active;

    public BudgetDto(Budget budget) {
        this.id = budget.getId();
        this.clubId = budget.getClubId();
        this.amount = budget.getAmount();
        this.active = budget.getExpiredAt()==null;
    }
}
