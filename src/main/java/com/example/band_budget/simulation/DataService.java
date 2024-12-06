package com.example.band_budget.simulation;


import com.example.band_budget.PayBook.PayBook;
import com.example.band_budget.PayBook.PayBookStore;
import com.example.band_budget.PayMember.PayMember;
import com.example.band_budget.PayMember.PayMemberStore;
import com.example.band_budget.simulation.command.CreatePayBookDummy;
import com.example.band_budget.simulation.command.RegisterPayMemberDummy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DataService {

    private final PayBookStore payBookStore;
    private final PayMemberStore payMemberStore;

    public Long createPayBookDummy(CreatePayBookDummy command){
        return payBookStore.save(command.getUsername(), new PayBook(command)).getId();
    }

    public Long registerPayMemberDummy(RegisterPayMemberDummy command){

            PayBook payBook = payBookStore.find(command.getPayBookId());
            command.setPayBook(payBook);
            PayMember payMember = payMemberStore.save(command.getUsername(), new PayMember(command));

            return payMember.getId();
    }

}
