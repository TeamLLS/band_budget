package com.example.band_budget.PayBook.policy;

import com.example.band_budget.PayBook.PayBook;
import com.example.band_budget.PayBook.PayBookStatus;
import com.example.band_budget.PayBook.exception.PayBookNotClosedException;
import com.example.band_budget.PayBook.exception.PayBookNotOpenedException;

public class PayBookStatusAccessPolicy {
    public static void isOpened(PayBook payBook){
        if(payBook.getStatus() != PayBookStatus.OPENED){
            throw new PayBookNotOpenedException("유효 장부 아님", payBook.getId(), payBook.getStatus().getDisplay());
        }
    }


    public static void isClosed(PayBook payBook){
        if(payBook.getStatus() != PayBookStatus.CLOSED){
            throw new PayBookNotClosedException("유효 장부 아님", payBook.getId(), payBook.getStatus().getDisplay());
        }
    }
}
