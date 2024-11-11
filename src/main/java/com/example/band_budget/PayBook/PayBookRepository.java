package com.example.band_budget.PayBook;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PayBookRepository extends JpaRepository<PayBook, Long> {

    @Query("SELECT p FROM PayBook p WHERE p.clubId = :clubId")
    public Page<PayBook> findAllByClubId(@Param("clubId") Long clubId, Pageable pageable);
}
