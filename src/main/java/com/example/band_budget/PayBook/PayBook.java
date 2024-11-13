package com.example.band_budget.PayBook;

import com.example.band_budget.PayBook.command.CancelPayBook;
import com.example.band_budget.PayBook.command.ClosePayBook;
import com.example.band_budget.PayBook.command.CreatePayBook;
import com.example.band_budget.PayBook.event.PayBookCanceled;
import com.example.band_budget.PayBook.event.PayBookClosed;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
public class PayBook {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Long clubId;
    private String name;
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PayBookStatus status;

    @NotNull
    private Integer amount;

    private Instant createdAt;
    private Instant closedAt;

    public PayBook(CreatePayBook command) {
        this.clubId = command.getClubId();
        this.name = command.getName();
        this.description = command.getDescription();
        this.amount = command.getAmount();
        this.status = PayBookStatus.OPENED;
        this.createdAt = Instant.now();
    }

    public PayBookCanceled cancel(CancelPayBook command){
        this.status = PayBookStatus.CANCELED;
        this.closedAt = Instant.now();

        return new PayBookCanceled(command.getUsername(), this);
    }

    public PayBookClosed close(ClosePayBook command){
        this.status = PayBookStatus.CLOSED;
        this.closedAt = Instant.now();

        return new PayBookClosed(command.getUsername(), this);
    }
}
