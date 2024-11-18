package com.example.band_budget.PayMember.command;


import com.example.band_budget.PayBook.PayBook;
import com.example.band_budget.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterPayMember extends Command {

    @NotNull
    private Long payBookId;
    private PayBook payBook; //반드시 별도 세팅
    @NotNull
    private Long memberId;
    private String memberName;
    @NotNull
    private String profileName;


    public RegisterPayMember(String username, Long payBookId, Long clubId, Long memberId, String memberName, String profileName) {
        super(username, clubId);
        this.payBookId = payBookId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.profileName = profileName;
    }

    public void setPayBook(PayBook payBook) {
        this.payBook = payBook;
    }
}
