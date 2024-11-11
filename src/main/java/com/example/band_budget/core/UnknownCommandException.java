package com.example.band_budget.core;

import lombok.Getter;

@Getter
public class UnknownCommandException extends RuntimeException{

    private String requester;

    public UnknownCommandException(String message, String requester){
        super(message);
        this.requester=requester;
    }


    public UnknownCommandException(String message) {
        super(message);
    }

    public UnknownCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownCommandException(Throwable cause) {
        super(cause);
    }
}
