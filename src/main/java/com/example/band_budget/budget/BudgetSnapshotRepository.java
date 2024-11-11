package com.example.band_budget.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface BudgetSnapshotRepository extends JpaRepository<BudgetSnapshot, Long> {

    @Query(" SELECT s FROM BudgetSnapshot s WHERE s.clubId =:clubId AND s.time <:time ORDER BY s.time DESC")
    public Optional<BudgetSnapshot> findFirstByClubIdAndTime(@Param("clubId") Long clubId, @Param("time") Instant time);

    Optional<BudgetSnapshot> findFirstByClubIdAndTimeLessThanOrderByTimeDesc(Long clubId, Instant time);
}
