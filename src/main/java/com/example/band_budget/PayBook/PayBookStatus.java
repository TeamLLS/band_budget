package com.example.band_budget.PayBook;

public enum PayBookStatus {
    OPENED("모금중"),
    CANCELED("취소됨"),
    CLOSED("모금종료");

    private final String display;

    PayBookStatus(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

}
