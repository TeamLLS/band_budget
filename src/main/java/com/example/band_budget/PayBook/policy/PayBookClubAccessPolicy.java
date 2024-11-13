package com.example.band_budget.PayBook.policy;

import com.example.band_budget.PayBook.PayBook;
import com.example.band_budget.PayBook.exception.PayBookNotInClubException;

public class PayBookClubAccessPolicy {
    public static void isInClub(PayBook payBook, Long clubId) {
        if (!payBook.getClubId().equals(clubId)) {
            throw new PayBookNotInClubException("해당 모임의 장부 아님", payBook.getId(), clubId);
        }
    }
}
