package com.example.band_budget.PayBook.form;

import com.example.band_budget.PayBook.PayBook;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
public class PayBookItemDto {

    private Long id;
    private String name;
    private String status;
    private Instant createdAt;

    public PayBookItemDto(PayBook payBook) {
        this.id = payBook.getId();
        this.name = payBook.getName();
        this.status = payBook.getStatus().getDisplay();
        this.createdAt = payBook.getCreatedAt();
    }
}
