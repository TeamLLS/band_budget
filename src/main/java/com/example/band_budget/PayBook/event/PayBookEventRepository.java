package com.example.band_budget.PayBook.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PayBookEventRepository extends JpaRepository<PayBookEventJpo, String> {
}
