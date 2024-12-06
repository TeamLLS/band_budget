package com.example.band_budget.budget.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface BudgetEventRepository extends JpaRepository<BudgetEventJpo, String> {

    public Integer countByClubId(Long clubId);

    @Query("SELECT e FROM BudgetEventJpo e WHERE e.clubId = :clubId AND e.time <= :time ORDER BY e.time DESC")
    public Page<BudgetEventJpo> findAllByClubIdAndTime(@Param("clubId")Long clubId, @Param("time") Instant time, Pageable pageable);

    @Query("SELECT e FROM BudgetEventJpo e WHERE e.clubId = :clubId AND e.time > :startTime AND e.time <= :endTime ORDER BY e.time ASC")
    public List<BudgetEventJpo> findAllByClubIdAndTimeRange(
            @Param("clubId")Long clubId,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime);
}
