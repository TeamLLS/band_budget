package com.example.band_budget.PayMember;


import com.example.band_budget.PayBook.PayBook;
import com.example.band_budget.PayBook.PayBookStore;
import com.example.band_budget.PayBook.policy.PayBookClubAccessPolicy;
import com.example.band_budget.PayBook.policy.PayBookStatusAccessPolicy;
import com.example.band_budget.PayMember.command.ChangePayMemberStatus;
import com.example.band_budget.PayMember.command.ConfirmPayMember;
import com.example.band_budget.PayMember.command.RegisterPayMember;
import com.example.band_budget.PayMember.form.PayBookRecord;
import com.example.band_budget.PayMember.form.PayRecord;
import com.example.band_budget.budget.BudgetService;
import com.example.band_budget.budget.command.UpdateBudget;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class PayMemberService {

    private final PayBookStore payBookStore;
    private final PayMemberStore payMemberStore;
    private final BudgetService budgetService;

    public Long registerPayMember(RegisterPayMember command){
        PayMember payMember;
        payMember = payMemberStore.find(command.getPayBookId(), command.getMemberId());

        if (payMember == null){
            PayBook payBook = payBookStore.find(command.getPayBookId());

            PayBookStatusAccessPolicy.isOpened(payBook);
            PayBookClubAccessPolicy.isInClub(payBook, command.getClubId());

            command.setPayBook(payBook);

            payMember = payMemberStore.save(command.getUsername(), new PayMember(command));
        }

        return payMember.getId();
    }

    public Long changePayMemberStatus(ChangePayMemberStatus command){
        PayMember payMember = payMemberStore.find(command.getPayBookId(), command.getMemberId());

        if(payMember==null){
            throw new NoSuchElementException();
        }

        PayBook payBook = payMember.getPayBook();

        if(command.getStatus() == PayStatus.LATE_PAID && payMember.getStatus() == PayStatus.UNPAID) {
            PayBookStatusAccessPolicy.isClosed(payBook);
            payMemberStore.saveEvent(payMember.update(command));
            payMemberStore.saveEvent(payMember.confirm(new ConfirmPayMember(command.getUsername(), payBook.getId())));
            budgetService.updateBudget(new UpdateBudget(command.getUsername(), payBook.getClubId(), payBook.getName() + "-additional", payBook.getAmount()));

        } else if(command.getStatus() != PayStatus.LATE_PAID && payMember.getStatus() != command.getStatus()){
            PayBookStatusAccessPolicy.isOpened(payBook);
            payMemberStore.saveEvent(payMember.update(command));
        }

        return payMember.getId();
    }

    public Integer confirmPayMember(ConfirmPayMember command){
        Page<PayMember> page;

        int pageNo=0;

        do{
            page = payMemberStore.findListByPayBookId(command.getPayBookId(), pageNo, 2);
            page.getContent().forEach(p -> payMemberStore.saveEvent(p.confirm(command)));

            pageNo++;
        }while (page.hasNext());

        return (int) page.getTotalElements();
    }


    @Transactional(readOnly = true)
    public List<PayRecord> getPayRecordList(Long payBookId, int pageNo, int pageSize){
        return payMemberStore.findListByPayBookId(payBookId, pageNo, pageSize).getContent()
                .stream().map(PayRecord::new).toList();
    }

    @Transactional(readOnly = true)
    public List<PayBookRecord> getPayBookRecordList(Long clubId, String username, int pageNo, int pageSize){
        return payMemberStore.findListWithPayBookByUsername(clubId, username, pageNo, pageSize).getContent()
                .stream().map(PayBookRecord::new).toList();
    }
}
