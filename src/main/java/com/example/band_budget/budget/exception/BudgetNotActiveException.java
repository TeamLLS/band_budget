package com.example.band_budget.budget.exception;

import lombok.Getter;

@Getter
public class BudgetNotActiveException extends RuntimeException{

    private Long budgetId;

    public BudgetNotActiveException(String message, Long budgetId) {
        super(message);
        this.budgetId = budgetId;
    }

    public BudgetNotActiveException(String message) {
        super(message);
    }

    public BudgetNotActiveException(String message, Throwable cause, Long budgetId) {
        super(message, cause);
        this.budgetId = budgetId;
    }

    public BudgetNotActiveException(Throwable cause, Long budgetId) {
        super(cause);
        this.budgetId = budgetId;
    }
}
