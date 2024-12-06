package com.example.band_budget.PayBook.event;

import com.example.band_budget.PayBook.PayBook;
import com.example.band_budget.PayBook.PayBookStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class PayBookCanceled extends PayBookEvent{


    public PayBookCanceled(String username, PayBook payBook) {
        super(UUID.randomUUID().toString(), payBook.getId(), payBook.getClubId(), username, payBook.getClosedAt());
    }
}
