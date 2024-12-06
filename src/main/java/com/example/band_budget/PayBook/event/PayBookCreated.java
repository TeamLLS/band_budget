package com.example.band_budget.PayBook.event;

import com.example.band_budget.PayBook.PayBook;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class PayBookCreated extends PayBookEvent {

    private String name;
    private String description;
    private Instant deadline;


    public PayBookCreated(String username, PayBook payBook) {
        super(UUID.randomUUID().toString(), payBook.getId(), payBook.getClubId(), username, payBook.getCreatedAt());
        this.name = payBook.getName();
        this.description = payBook.getDescription();
        this.deadline = payBook.getDeadline();
    }
}
