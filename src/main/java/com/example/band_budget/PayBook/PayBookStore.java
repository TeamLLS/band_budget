package com.example.band_budget.PayBook;


import com.example.band_budget.PayBook.event.PayBookCreated;
import com.example.band_budget.PayBook.event.PayBookEvent;
import com.example.band_budget.PayBook.event.PayBookEventJpo;
import com.example.band_budget.PayBook.event.PayBookEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayBookStore {

    private final PayBookRepository payBookRepository;
    private final PayBookEventRepository payBookEventRepository;

    public PayBook save(String username, PayBook payBook){
        PayBook saved = payBookRepository.save(payBook);
        saveEvent(new PayBookCreated(username, saved));
        return saved;
    }

    public PayBook find(Long payBookId) {
        return payBookRepository.findById(payBookId).orElseThrow();
    }

    public Page<PayBook> findListByClubId(Long clubId, int pageNo, int pageSize){
        return payBookRepository.findAllByClubId(clubId, PageRequest.of(pageNo, pageSize));
    }

    public PayBookEventJpo saveEvent(PayBookEvent event){
        return payBookEventRepository.save(new PayBookEventJpo(event));
    }

    //테스트 용도
    public PayBookEventJpo findEvent(String eventId){
        return payBookEventRepository.findById(eventId).orElseThrow();
    }

}
