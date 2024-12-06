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
    private Integer amount;
    private String status;
    private Instant deadline;

    public PayBookItemDto(PayBook payBook) {
        this.id = payBook.getId();
        this.name = payBook.getName();
        this.amount = payBook.getAmount();
        this.status = payBook.getStatus().getDisplay();
        this.deadline = payBook.getDeadline();
    }
}
