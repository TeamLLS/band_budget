package com.example.band_budget.external.kafka;


import com.example.band_budget.PayBook.PayBookService;
import com.example.band_budget.PayBook.command.CancelPayBook;
import com.example.band_budget.PayBook.command.ClosePayBook;
import com.example.band_budget.PayBook.command.CreatePayBook;
import com.example.band_budget.PayMember.PayMemberService;
import com.example.band_budget.PayMember.command.UpdatePayMember;
import com.example.band_budget.PayMember.command.RegisterPayMember;
import com.example.band_budget.budget.BudgetService;
import com.example.band_budget.budget.command.CloseBudget;
import com.example.band_budget.budget.command.CreateBudget;
import com.example.band_budget.budget.command.UpdateBudget;
import com.example.band_budget.core.Command;
import com.example.band_budget.core.UnknownCommandException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "budget-topic", groupId = "budget-consumer-group")
public class KafkaConsumerService {

    private final BudgetService budgetService;
    private final PayBookService payBookService;
    private final PayMemberService payMemberService;

    @KafkaHandler
    public void consumeCommand(CreateBudget command){
        budgetService.createBudget(command);
    }

    @KafkaHandler
    public void consumeCommand(UpdateBudget command){
        budgetService.updateBudget(command);
    }

    @KafkaHandler
    public void consumeCommand(CloseBudget command){
        budgetService.closeBudget(command);
    }

    @KafkaHandler
    public void consumeCommand(CreatePayBook command){
        payBookService.createPayBook(command);
    }

    @KafkaHandler
    public void consumeCommand(CancelPayBook command){
        payBookService.cancelPayBook(command);
    }

    @KafkaHandler
    public void consumeCommand(ClosePayBook command){
        payBookService.closePayBook(command);
    }

    @KafkaHandler
    public void consumeCommand(RegisterPayMember command){
        payMemberService.registerPayMember(command);
    }

    @KafkaHandler
    public void consumeCommand(UpdatePayMember command){
        payMemberService.updatePayMember(command);
    }


    @KafkaHandler(isDefault = true)
    public void consume(Command command, Acknowledgment acknowledgment){
        throw new UnknownCommandException("알 수 없는 명령", command.getUsername());
    }
}
