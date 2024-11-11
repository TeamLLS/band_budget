package com.example.band_budget.PayBook.exception;

import lombok.Getter;

@Getter
public class PayBookNotOpenedException extends RuntimeException{

    private Long payBookId;

    private String current;

    public PayBookNotOpenedException(String message, Long payBookId, String status) {
        super(message);
        this.payBookId = payBookId;
        this.current = status;
    }

    public PayBookNotOpenedException(String message) {
        super(message);
    }

    public PayBookNotOpenedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayBookNotOpenedException(Throwable cause) {
        super(cause);
    }
}
