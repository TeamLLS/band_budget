package com.example.band_budget.budget;

import com.example.band_budget.budget.event.*;
import com.example.band_budget.external.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BudgetStore {

    private final KafkaProducerService producerService;
    private final BudgetRepository budgetRepository;
    private final BudgetSnapshotRepository budgetSnapshotRepository;
    private final BudgetEventRepository budgetEventRepository;

    public Budget save(String username, Budget budget){
        Budget saved = budgetRepository.save(budget);
        budgetSnapshotRepository.save(saved.snapshot(saved.getCreatedAt()));
        saveEvent(new BudgetCreated(username, saved));

        return saved;
    }

    public Budget findByClubId(Long clubId){
        return budgetRepository.findByClubId(clubId).orElseThrow();
    }

    public Budget findByClubIdAndTime(Long clubId, Instant time){

        //BudgetSnapshot snapshot = budgetSnapshotRepository.findFirstByClubIdAndTime(clubId, time).orElseThrow();

        BudgetSnapshot snapshot = budgetSnapshotRepository.findFirstByClubIdAndTimeLessThanOrderByTimeDesc(clubId, time).orElseThrow();
        List<BudgetEventJpo> events = budgetEventRepository.findAllByClubIdAndTimeRange(clubId, snapshot.getTime(), time);

        Budget budget = snapshot.toBudget();

        for (BudgetEventJpo event : events) {
            budget.restore(event.toEvent());
        }

        return budget;
    }

    public BudgetEventJpo saveEvent(BudgetEvent event){
        BudgetEventJpo saved = budgetEventRepository.save(new BudgetEventJpo(event));

        if(event instanceof BudgetUpdated){
            producerService.sendEventToKafka(event);
        }

        return saved;
    }

    public Page<BudgetEventJpo> findEventListByClubId(Long clubId, Instant time, int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return budgetEventRepository.findAllByClubIdAndTime(clubId, time, pageable);
    }

    public Integer countEventByClubId(Long clubId){
        return budgetEventRepository.countByClubId(clubId);
    }

    public BudgetSnapshot saveSnapshot(BudgetSnapshot snapshot){
        return budgetSnapshotRepository.save(snapshot);
    }



}
