package com.example.band_budget.PayBook.form;


import com.example.band_budget.PayBook.PayBook;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PayBookDto {

    private Long id;

    private Long clubId;
    private String name;

    private String description;

    private String status;
    private Integer amount;

    private String createdBy;
    private Instant createdAt;
    private Instant closedAt;
    private Instant deadline;


    public PayBookDto(PayBook payBook) {
        this.id = payBook.getId();
        this.clubId = payBook.getClubId();
        this.name = payBook.getName();
        this.description = payBook.getDescription();
        this.status = payBook.getStatus().getDisplay();
        this.amount = payBook.getAmount();
        this.createdBy = payBook.getCreatedBy();
        this.createdAt = payBook.getCreatedAt();
        this.closedAt = payBook.getClosedAt();
        this.deadline = payBook.getDeadline();
    }
}
