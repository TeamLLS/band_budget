package com.example.band_budget.PayBook.exception;

import lombok.Getter;

@Getter
public class PayBookNotClosedException extends RuntimeException{

    private Long payBookId;

    private String current;

    public PayBookNotClosedException(String message, Long payBookId, String status) {
        super(message);
        this.payBookId = payBookId;
        this.current = status;
    }

    public PayBookNotClosedException(String message) {
        super(message);
    }

    public PayBookNotClosedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayBookNotClosedException(Throwable cause) {
        super(cause);
    }
}
