package com.example.band_budget.PayBook.command;

import com.example.band_budget.core.Command;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class CreatePayBook extends Command {

    private Integer amount;
    private String name;
    private String memberName;
    private String description;
    private Instant deadline;


    public CreatePayBook(String username, Long clubId, Integer amount, String name, String memberName, String description, Instant deadline) {
        super(username, clubId);
        this.amount = amount;
        this.name = name;
        this.memberName = memberName;
        this.description = description;
        this.deadline = deadline;
    }
}
