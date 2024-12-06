package com.example.band_budget.PayMember;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PayMemberRepository extends JpaRepository<PayMember, Long> {

    public Optional<PayMember> findByPayBookIdAndMemberId(Long payBookId, Long memberId);

    @Query("SELECT p FROM PayMember p WHERE p.payBook.id =:payBookId AND p.status <> 'EXCLUDED'")
    public Page<PayMember> findAllByPayBookId(@Param("payBookId") Long payBookId, Pageable pageable);

    @Query("SELECT p FROM PayMember p WHERE p.payBook.id =:payBookId AND p.status = 'UNPAID'")
    public Page<PayMember> findUnPayByPayBookId(@Param("payBookId") Long payBookId, Pageable pageable);

    @Query("SELECT pm FROM PayMember pm JOIN FETCH pm.payBook pb WHERE pb.clubId =:clubId AND pm.username =:username AND pm.status <> 'EXCLUDED'")
    public Page<PayMember> findAllWithPayBookByUsername(@Param("clubId") Long clubId, @Param("username") String username, Pageable pageable);

    @Query("SELECT pm FROM PayMember pm JOIN FETCH pm.payBook pb WHERE pb.clubId =:clubId AND pm.username =:username AND pm.status = 'UNPAID'")
    public Page<PayMember> findUnPayWithPayBookByUsername(@Param("clubId") Long clubId, @Param("username") String username, Pageable pageable);
}
