package com.example.band_budget.budget.policy;


import com.example.band_budget.budget.Budget;
import com.example.band_budget.budget.exception.BudgetNotActiveException;
import com.example.band_budget.budget.form.BudgetDto;

public class BudgetStatusAccessPolicy {

    public static void isActive(Budget budget){
        if(budget.getExpiredAt()!=null){
            throw new BudgetNotActiveException("유효 예산 아님", budget.getId());
        }
    }

    public static void isActive(BudgetDto budget){
        if(!budget.getActive()){
            throw new BudgetNotActiveException("유효 예산 아님", budget.getId());
        }
    }

}
