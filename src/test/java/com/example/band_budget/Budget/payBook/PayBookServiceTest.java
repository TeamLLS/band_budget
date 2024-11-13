package com.example.band_budget.Budget.payBook;


import com.example.band_budget.PayBook.PayBook;
import com.example.band_budget.PayBook.PayBookService;
import com.example.band_budget.PayBook.PayBookStatus;
import com.example.band_budget.PayBook.PayBookStore;
import com.example.band_budget.PayBook.command.CancelPayBook;
import com.example.band_budget.PayBook.command.ClosePayBook;
import com.example.band_budget.PayBook.command.CreatePayBook;
import com.example.band_budget.PayBook.form.PayBookDto;
import com.example.band_budget.PayBook.form.PayBookItemDto;
import com.example.band_budget.PayMember.PayMemberService;
import com.example.band_budget.budget.BudgetService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Import({PayBookService.class, PayBookStore.class})
public class PayBookServiceTest {

    @MockBean
    PayBookStore payBookStore;

    @Autowired
    PayBookService payBookService;

    List<PayBook> payBooks;


    @BeforeEach
    public void savePayBooks(){
        PayBook saved1 = new PayBook(new CreatePayBook("TestManager", 1L, 11000, "November", "for dues1"));
        PayBook saved2 = new PayBook(new CreatePayBook("TestManager", 1L, 10000, "October", "for dues2"));
        PayBook saved3 = new PayBook(new CreatePayBook("TestManager", 1L, 12000, "December", "for dues3"));

        payBooks = new ArrayList<>();

        payBooks.add(0, saved1);
        payBooks.add(1, saved2);
        payBooks.add(2, saved3);

        Mockito.when(payBookStore.find(2L)).thenReturn(payBooks.get(1));
        Mockito.when(payBookStore.find(3L)).thenReturn(payBooks.get(2));

        Mockito.when(payBookStore.findListByClubId(1L, 0, 2))
                .thenReturn(new PageImpl<>(List.of(payBooks.get(0), payBooks.get(1)), PageRequest.of(0, 2),3));
        Mockito.when(payBookStore.findListByClubId(1L, 1, 2))
                .thenReturn(new PageImpl<>(List.of(payBooks.get(2)), PageRequest.of(1, 2),3));
    }

    @Test
    public void cancelTest(){
        PayBook payBook = payBooks.get(1);

        payBookService.cancelPayBook(new CancelPayBook("TestManager", 2L, payBook.getClubId()));

        PayBookDto find = payBookService.getPayBookInfo(2L);
        PayBookDto dto = new PayBookDto(payBook);

        Assertions.assertThat(find)
                .hasFieldOrPropertyWithValue("id", dto.getId())
                .hasFieldOrPropertyWithValue("clubId", dto.getClubId())
                .hasFieldOrPropertyWithValue("name", dto.getName())
                .hasFieldOrPropertyWithValue("description", dto.getDescription())
                .hasFieldOrPropertyWithValue("status", PayBookStatus.CANCELED.getDisplay())
                .hasFieldOrPropertyWithValue("amount", dto.getAmount())
                .hasFieldOrPropertyWithValue("createdAt", dto.getCreatedAt())
                .hasFieldOrPropertyWithValue("closedAt", dto.getClosedAt());
    }

    @Test
    public void closeTest(){
        PayBook payBook = payBooks.get(2);

        payBookService.closePayBook(new ClosePayBook("TestManager", 3L, payBook.getClubId()));

        PayBookDto find = payBookService.getPayBookInfo(3L);
        PayBookDto dto = new PayBookDto(payBook);

        Assertions.assertThat(find)
                .hasFieldOrPropertyWithValue("id", dto.getId())
                .hasFieldOrPropertyWithValue("clubId", dto.getClubId())
                .hasFieldOrPropertyWithValue("name", dto.getName())
                .hasFieldOrPropertyWithValue("description", dto.getDescription())
                .hasFieldOrPropertyWithValue("status", PayBookStatus.CLOSED.getDisplay())
                .hasFieldOrPropertyWithValue("amount", dto.getAmount())
                .hasFieldOrPropertyWithValue("createdAt", dto.getCreatedAt())
                .hasFieldOrPropertyWithValue("closedAt", dto.getClosedAt());
    }

    @Test
    public void getListTest(){
        List<PayBookItemDto> listA = payBookService.getPayBookList(1L, 0, 2);
        List<PayBookItemDto> listB = payBookService.getPayBookList(1L, 1, 2);

        PayBookItemDto dto = new PayBookItemDto(payBooks.get(2));

        Assertions.assertThat(listA.size()).isEqualTo(2);
        Assertions.assertThat(listB.size()).isEqualTo(1);
        Assertions.assertThat(listB.get(0))
                .hasFieldOrPropertyWithValue("id", dto.getId())
                .hasFieldOrPropertyWithValue("name", dto.getName())
                .hasFieldOrPropertyWithValue("status", dto.getStatus())
                .hasFieldOrPropertyWithValue("createdAt", dto.getCreatedAt());
    }
}
