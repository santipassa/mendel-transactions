package com.mendel.transactions.unit;

import com.mendel.transactions.dto.SumDTO;
import com.mendel.transactions.dto.TransactionDTO;
import com.mendel.transactions.dto.TransactionInputDTO;
import com.mendel.transactions.models.Transaction;
import com.mendel.transactions.repositories.transaction.TransactionRepository;
import com.mendel.transactions.services.TransactionsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
public class TransactionsServiceUnitTests {

    @MockBean
    private TransactionRepository transactionRepository;

    @Autowired
    TransactionsService transactionsService;

    @Test
    public void whenSaveTransactionThenIsSavedSuccessfully() {
        when(transactionRepository.save(any())).thenReturn(Transaction.builder()
                .id(1L)
                .amount(1000.0)
                .type("shopping")
                .build());
        TransactionDTO transactionDTO = transactionsService.saveTransaction(1L,
                TransactionInputDTO.builder()
                        .amount(1000.0)
                        .type("shopping")
                        .build()
        );
        assertEquals(1, transactionDTO.getId());
        assertEquals(1000.0, transactionDTO.getAmount());
        assertEquals("shopping", transactionDTO.getType());
    }

    @Test
    public void whenFindTransactionsIdsByTypeThenListOfIdsAreReturned() {
        when(transactionRepository.findTransactionsByType(any()))
                .thenReturn(List.of(Transaction.builder()
                        .id(2L)
                        .amount(1000.0)
                        .type("shopping")
                        .build()));
        List<Long> transactionsIds = transactionsService.findTransactionsIdsByType("shopping");
        assertEquals(1, transactionsIds.size());
        assertEquals(2, transactionsIds.get(0));
    }

    @Test
    public void whenSumTransactionAmountThenCorrectValueIsReturned() {
        when(transactionRepository.findAll())
                .thenReturn(List.of(Transaction.builder()
                                .id(2L)
                                .amount(1000.0)
                                .type("shopping")
                                .build(),
                        Transaction.builder()
                                .id(3L)
                                .amount(2000.0)
                                .type("shopping")
                                .parentId(2L)
                                .build()));
        SumDTO sumDTO = transactionsService.sumRelatedTransactionsAmounts(2L);
        assertEquals(3000.0, sumDTO.getSum());
    }
}
