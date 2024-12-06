package com.example.band_budget;


import com.example.band_budget.PayBook.PayBook;
import com.example.band_budget.PayBook.PayBookRepository;
import com.example.band_budget.PayBook.command.CreatePayBook;
import com.example.band_budget.PayMember.PayMember;
import com.example.band_budget.PayMember.PayMemberRepository;
import com.example.band_budget.PayMember.command.RegisterPayMember;
import com.example.band_budget.budget.Budget;
import com.example.band_budget.budget.BudgetRepository;
import com.example.band_budget.budget.BudgetSnapshotRepository;
import com.example.band_budget.budget.command.CreateBudget;
import com.example.band_budget.budget.command.UpdateBudget;
import com.example.band_budget.budget.event.BudgetEventJpo;
import com.example.band_budget.budget.event.BudgetEventRepository;
import com.example.band_budget.budget.event.BudgetUpdated;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class DummyUtil {

    private final BudgetRepository budgetRepository;
    private final BudgetSnapshotRepository budgetSnapshotRepository;
    private final BudgetEventRepository budgetEventRepository;
    private final PayBookRepository payBookRepository;
    private final PayMemberRepository payMemberRepository;

    @Transactional
    @PostConstruct
    public void makeDummy(){

        CreateBudget commandB = new CreateBudget("Dummy_userB", 1L);

        Budget budget = budgetRepository.save(new Budget(commandB));
        budgetSnapshotRepository.save(budget.snapshot(budget.getCreatedAt()));


        String[] descriptions = {"test income1", "test expense1", "test income2", "test expense2", "test income3", "test expense3"};
        Integer[] amount = {10000, -7000, 5000, -6000, 7000, 2000};

        UpdateBudget commandU;
        BudgetUpdated eventU;

        for(int i=0; i<6; i++){
            commandU = new UpdateBudget("Dummy_userB", 1L, descriptions[i], amount[i]);
            eventU = budget.update(commandU);
            eventU.setTime(Instant.now().plusSeconds(86400*i));
            budgetEventRepository.save(new BudgetEventJpo(eventU));
            if(i==4){
                budgetSnapshotRepository.save(budget.snapshot(eventU.getTime()));
            }
        }

        CreatePayBook commandPB1 = new CreatePayBook("Dummy_userB", 1L, 10000, "TestManager", "October", "for october dues", Instant.now().plusSeconds(720000));
        CreatePayBook commandPB2 = new CreatePayBook("Dummy_userB", 1L, 11000, "TestManager", "November", "for november dues", Instant.now().plusSeconds(1440000));

        PayBook pb1 = payBookRepository.save(new PayBook(commandPB1));
        PayBook pb2 = payBookRepository.save(new PayBook(commandPB2));


        RegisterPayMember commandPM;


        Long[] targetIds1 = {1L, 2L, 3L};
        String[] targetNames1 = {"허연준", "임윤빈", "권미르"};
        String[] targetProfiles1 = {"Dummy_userA", "Dummy_userB", "Dummy_userC"};

        Long[] targetIds2 = {2L, 4L, 5L};
        String[] targetNames2 = {"임윤빈", "최은", "하도준"};
        String[] targetProfiles2 = {"Dummy_userB", "Dummy_userD", "Dummy_userE"};


        for(int i=0; i<3; i++){
            commandPM = new RegisterPayMember("Dummy_userB", pb1.getId(), pb1.getClubId(), targetIds1[i], targetNames1[i], targetProfiles1[i]);
            commandPM.setPayBook(pb1);
            payMemberRepository.save(new PayMember(commandPM));
        }

        for(int i=0; i<3; i++){
            commandPM = new RegisterPayMember("Dummy_userB", pb2.getId(), pb2.getClubId(), targetIds2[i], targetNames2[i], targetProfiles2[i]);
            commandPM.setPayBook(pb2);
            payMemberRepository.save(new PayMember(commandPM));
        }
    }



}
