package com.example.band_budget.budget;

import com.example.band_budget.budget.form.BudgetRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/budget")
public class BudgetController {

    private final BudgetService budgetService;


    @GetMapping("/{clubId}")
    public ResponseEntity<?> getBudgetInfo(@PathVariable Long clubId, @RequestParam(required = false) Instant time){

        return ResponseEntity.ok().body(budgetService.getBudgetInfo(clubId, time));
    }

    @GetMapping("/{clubId}/records")
    public ResponseEntity<?> getRecords(@PathVariable Long clubId, @RequestParam(required = false) Instant time, @RequestParam int pageNo){

        List<BudgetRecord> records = budgetService.getRecords(clubId, time, pageNo, 50);

        Map<String, Object> result = new HashMap<>();

        result.put("list", records);

        return ResponseEntity.ok().body(result);
    }
}
