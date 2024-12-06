package com.example.band_budget.PayMember;

import com.example.band_budget.PayMember.form.PayBookRecord;
import com.example.band_budget.PayMember.form.PayRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/paymember")
@RequiredArgsConstructor
public class PayMemberController {

    private final PayMemberService payMemberService;

    @GetMapping("/{payBookId}/list")
    public ResponseEntity<?> getPayMemberList(@PathVariable Long payBookId, @RequestParam int pageNo, @RequestParam(required = false) Boolean unPay){
        List<PayRecord> list = payMemberService.getPayRecordList(payBookId, pageNo, 50, unPay);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{clubId}/paybook/list")
    public ResponseEntity<?> getPayBookRecordList(@RequestHeader String username, @PathVariable Long clubId, @RequestParam int pageNo, @RequestParam(required = false) Boolean unPay){
        List<PayBookRecord> list = payMemberService.getPayBookRecordList(clubId, username, pageNo, 50, unPay);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);

        return ResponseEntity.ok().body(result);
    }
}
