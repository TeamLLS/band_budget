package com.example.band_budget.simulation.command;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
public class CreatePayBookDummy {

    @NotNull
    private String username;
    @NotNull
    private Long clubId;
    private Integer amount;
    private String name;
    private String memberName;
    private String description;
    private Instant deadline;
    private Instant time;

    public CreatePayBookDummy(String username, Long clubId, Integer amount, String name, String memberName, String description, Instant deadline, Instant time) {
        this.username = username;
        this.clubId = clubId;
        this.amount = amount;
        this.name = name;
        this.memberName = memberName;
        this.description = description;
        this.deadline = deadline;
        this.time = time;
    }
}
