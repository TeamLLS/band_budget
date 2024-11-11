package com.example.band_budget.budget;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    public Optional<Budget> findByClubId(Long clubId);
}
