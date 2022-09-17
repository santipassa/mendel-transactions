package com.mendel.transactions.repositories.transaction;


import com.mendel.transactions.models.Transaction;
import com.mendel.transactions.repositories.Repository;

import java.util.List;

public interface TransactionRepository extends Repository<Transaction> {
    List<Transaction> findTransactionsByType(String type);
}
