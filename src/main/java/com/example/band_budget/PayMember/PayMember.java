package com.example.band_budget.PayMember;

import com.example.band_budget.PayBook.PayBook;
import com.example.band_budget.PayMember.command.UpdatePayMember;
import com.example.band_budget.PayMember.command.ConfirmPayMember;
import com.example.band_budget.PayMember.command.RegisterPayMember;
import com.example.band_budget.PayMember.event.PayMemberConfirmed;
import com.example.band_budget.PayMember.event.PayMemberStatusChanged;
import com.example.band_budget.simulation.command.RegisterPayMemberDummy;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Entity
@NoArgsConstructor
public class PayMember {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paybook_id")
    private PayBook payBook;

    @NotNull
    private String username;    //프로필 조회용

    @NotNull
    private Long memberId;
    private String memberName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PayStatus status;

    private Instant paidAt;

    public PayMember(RegisterPayMember command) {
        this.payBook = command.getPayBook();
        this.username = command.getProfileName();
        this.memberId = command.getMemberId();
        this.memberName = command.getMemberName();
        this.status = PayStatus.UNPAID;
    }


    public PayMember(RegisterPayMemberDummy command) {
        this.payBook = command.getPayBook();
        this.username = command.getProfileName();
        this.memberId = command.getMemberId();
        this.memberName = command.getMemberName();
        this.paidAt = command.getTime();
        this.status = command.getStatus();
    }

    public PayMemberStatusChanged update(UpdatePayMember command){
        this.paidAt = command.getTime();
        this.status = command.getStatus();

        return new PayMemberStatusChanged(command.getUsername(), this);
    }


    public PayMemberConfirmed confirm(ConfirmPayMember command){
        return new PayMemberConfirmed(command.getUsername(), this);
    }
}
