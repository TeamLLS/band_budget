package com.example.band_budget.PayBook;

import com.example.band_budget.PayBook.command.CancelPayBook;
import com.example.band_budget.PayBook.command.ClosePayBook;
import com.example.band_budget.PayBook.command.CreatePayBook;
import com.example.band_budget.PayBook.form.PayBookDto;
import com.example.band_budget.PayBook.form.PayBookItemDto;
import com.example.band_budget.PayBook.policy.PayBookClubAccessPolicy;
import com.example.band_budget.PayBook.policy.PayBookStatusAccessPolicy;
import com.example.band_budget.PayMember.PayMemberService;
import com.example.band_budget.PayMember.command.ConfirmPayMember;
import com.example.band_budget.budget.BudgetService;
import com.example.band_budget.budget.command.UpdateBudget;
import com.example.band_budget.budget.form.BudgetDto;
import com.example.band_budget.budget.policy.BudgetStatusAccessPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PayBookService {

    private final PayMemberService payMemberService;
    private final PayBookStore payBookStore;
    private final BudgetService budgetService;

    public Long createPayBook(CreatePayBook command){
        BudgetDto budget = budgetService.getBudgetInfo(command.getClubId(), null);
        BudgetStatusAccessPolicy.isActive(budget);

        return payBookStore.save(command.getUsername(), new PayBook(command)).getId();
    }

    public Long cancelPayBook(CancelPayBook command){
        PayBook payBook = payBookStore.find(command.getPayBookId());

        PayBookStatusAccessPolicy.isOpened(payBook);
        PayBookClubAccessPolicy.isInClub(payBook, command.getClubId());

        payBook.cancel(command);
        return payBook.getId();
    }

    public Long closePayBook(ClosePayBook command){
        PayBook payBook = payBookStore.find(command.getPayBookId());

        PayBookStatusAccessPolicy.isOpened(payBook);
        PayBookClubAccessPolicy.isInClub(payBook, command.getClubId());

        Integer num = payMemberService.confirmPayMember(new ConfirmPayMember(command.getUsername(), payBook.getId()));
        budgetService.updateBudget(new UpdateBudget(command.getUsername(), payBook.getClubId(), payBook.getName(), num * payBook.getAmount()));

        payBookStore.saveEvent(payBook.close(command));

        return payBook.getId();
    }

    @Transactional(readOnly = true)
    public List<PayBookItemDto> getPayBookList(Long clubId, int pageNo, int pageSize){
        return payBookStore.findListByClubId(clubId, pageNo, pageSize).getContent()
                .stream().map(PayBookItemDto::new).toList();
    }

    @Transactional(readOnly = true)
    public PayBookDto getPayBookInfo(Long payBookId){
        return new PayBookDto(payBookStore.find(payBookId));
    }
}
