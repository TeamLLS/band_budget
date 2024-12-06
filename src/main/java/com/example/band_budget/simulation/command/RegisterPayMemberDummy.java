package com.example.band_budget.simulation.command;


import com.example.band_budget.PayBook.PayBook;
import com.example.band_budget.PayMember.PayStatus;
import com.example.band_budget.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
public class RegisterPayMemberDummy{

    @NotNull
    private String username;
    @NotNull
    private Long clubId;
    @NotNull
    private Long payBookId;
    private PayBook payBook; //반드시 별도 세팅
    @NotNull
    private Long memberId;
    private String memberName;
    @NotNull
    private String profileName;
    private PayStatus status;
    private Instant time;


    public RegisterPayMemberDummy(String username, Long clubId, Long payBookId, PayBook payBook, Long memberId, String memberName, String profileName, PayStatus status, Instant time) {
        this.username = username;
        this.clubId = clubId;
        this.payBookId = payBookId;
        this.payBook = payBook;
        this.memberId = memberId;
        this.memberName = memberName;
        this.profileName = profileName;
        this.status = status;
        this.time = time;
    }

    public void setPayBook(PayBook payBook) {
        this.payBook = payBook;
    }
}
