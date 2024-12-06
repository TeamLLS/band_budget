package com.example.band_budget.PayBook;

import com.example.band_budget.PayBook.form.PayBookItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping("/paybook")
@RequiredArgsConstructor
public class PayBookController {

    private final PayBookService payBookService;

    @GetMapping("/{clubId}/list")
    public ResponseEntity<?> getPayBookList(@PathVariable Long clubId, @RequestParam int pageNo){
        List<PayBookItemDto> list = payBookService.getPayBookList(clubId, pageNo, 50);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{payBookId}")
    public ResponseEntity<?> getPayBook(@PathVariable Long payBookId){
        return ResponseEntity.ok().body(payBookService.getPayBookInfo(payBookId));
    }
}
