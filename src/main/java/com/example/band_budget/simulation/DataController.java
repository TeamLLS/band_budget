package com.example.band_budget.simulation;

import com.example.band_budget.simulation.command.CreatePayBookDummy;
import com.example.band_budget.simulation.command.RegisterPayMemberDummy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/budget/dummy")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;


    @PostMapping("/paybook")
    public ResponseEntity<?> createPayBook(@Validated @RequestBody CreatePayBookDummy command){
        Long payBookId = dataService.createPayBookDummy(command);

        Map<String, Object> result = new HashMap<>();
        result.put("id", payBookId);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/paymember")
    public ResponseEntity<?> registerPayMember(@Validated @RequestBody RegisterPayMemberDummy command){
        dataService.registerPayMemberDummy(command);

        return ResponseEntity.ok().build();
    }
}
