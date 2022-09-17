package com.mendel.transactions.services;

import com.mendel.transactions.dto.SumDTO;
import com.mendel.transactions.dto.TransactionDTO;
import com.mendel.transactions.dto.TransactionInputDTO;

import java.util.List;

public interface TransactionsService {

    TransactionDTO saveTransaction(Long transactionId, TransactionInputDTO transaction);

    List<Long> findTransactionsIdsByType(String type);

    SumDTO sumRelatedTransactionsAmounts(Long transactionId);

}
