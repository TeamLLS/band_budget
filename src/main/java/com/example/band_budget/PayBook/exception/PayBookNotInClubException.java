package com.example.band_budget.PayBook.exception;

import lombok.Getter;

@Getter
public class PayBookNotInClubException extends RuntimeException{

    private Long payBookId;

    private Long clubId;

    public PayBookNotInClubException(String message, Long payBookId, Long clubId) {
        super(message);
        this.payBookId = payBookId;
        this.clubId = clubId;
    }

    public PayBookNotInClubException(String message) {
        super(message);
    }

    public PayBookNotInClubException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayBookNotInClubException(Throwable cause) {
        super(cause);
    }
}
