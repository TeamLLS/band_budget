package com.example.band_budget.core;


import com.example.band_budget.PayBook.command.CancelPayBook;
import com.example.band_budget.PayBook.command.ClosePayBook;
import com.example.band_budget.PayBook.command.CreatePayBook;
import com.example.band_budget.PayMember.command.ChangePayMemberStatus;
import com.example.band_budget.PayMember.command.RegisterPayMember;
import com.example.band_budget.budget.command.CloseBudget;
import com.example.band_budget.budget.command.CreateBudget;
import com.example.band_budget.budget.command.UpdateBudget;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreatePayBook.class, name = "CreatePayBook"),
        @JsonSubTypes.Type(value = ClosePayBook.class, name = "ClosePayBook"),
        @JsonSubTypes.Type(value = CancelPayBook.class, name = "CancelPayBook"),
        @JsonSubTypes.Type(value = RegisterPayMember.class, name = "RegisterPayMember"),
        @JsonSubTypes.Type(value = ChangePayMemberStatus.class, name = "ChangePayMemberStatus"),
        @JsonSubTypes.Type(value = CreateBudget.class, name = "CreateBudget"),
        @JsonSubTypes.Type(value = UpdateBudget.class, name = "UpdateBudget"),
        @JsonSubTypes.Type(value = CloseBudget.class, name = "CloseBudget"),
})
public abstract class Command {

    @NotNull
    private String username;
    @NotNull
    private Long clubId;

    public Command() {
    }

    public Command(String username, Long clubId) {
        this.username = username;
        this.clubId = clubId;
    }

    public String getUsername() {
        return username;
    }

    public Long getClubId() {
        return clubId;
    }
}
