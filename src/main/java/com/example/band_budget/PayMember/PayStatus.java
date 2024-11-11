package com.example.band_budget.PayMember;

public enum PayStatus {
    PAID("납부"),
    UNPAID("미납"),
    LATE_PAID("연체 납부"),
    EXCLUDED("제외");


    private final String display;

    PayStatus(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
