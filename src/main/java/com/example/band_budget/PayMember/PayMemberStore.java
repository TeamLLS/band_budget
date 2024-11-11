package com.example.band_budget.PayMember;

import com.example.band_budget.PayBook.event.PayBookEventJpo;
import com.example.band_budget.PayMember.event.PayMemberCreated;
import com.example.band_budget.PayMember.event.PayMemberEvent;
import com.example.band_budget.PayMember.event.PayMemberEventJpo;
import com.example.band_budget.PayMember.event.PayMemberEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PayMemberStore {

    private final PayMemberRepository payMemberRepository;
    private final PayMemberEventRepository payMemberEventRepository;

    public PayMember save(String username, PayMember payMember){
        PayMember saved = payMemberRepository.save(payMember);
        payMemberEventRepository.save(new PayMemberEventJpo(new PayMemberCreated(username, payMember)));

        return saved;
    }

    public PayMember find(Long payBookId, Long memberId){
        return payMemberRepository.findByPayBookIdAndMemberId(payBookId, memberId).orElseThrow();
    }

    public Page<PayMember> findListByPayBookId(Long payBookId, int pageNo, int pageSize){
        return payMemberRepository.findAllByPayBookId(payBookId, PageRequest.of(pageNo, pageSize));
    }

    public Page<PayMember> findListWithPayBookByUsername(Long clubId, String username, int pageNo, int pageSize){
        return payMemberRepository.findAllWithPayBookByUsername(clubId, username, PageRequest.of(pageNo, pageSize));
    }
    public PayMemberEventJpo saveEvent(PayMemberEvent event){
        return payMemberEventRepository.save(new PayMemberEventJpo(event));
    }


    //테스트 용도
    public PayMemberEventJpo findEvent(String eventId){
        return payMemberEventRepository.findById(eventId).orElseThrow();
    }
}
