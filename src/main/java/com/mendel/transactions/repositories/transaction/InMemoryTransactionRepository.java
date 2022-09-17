package com.mendel.transactions.repositories.transaction;

import com.mendel.transactions.exceptions.TransactionAlreadyExistsException;
import com.mendel.transactions.exceptions.TransactionNotFoundException;
import com.mendel.transactions.models.Transaction;
import com.mendel.transactions.repositories.datasources.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class InMemoryTransactionRepository implements TransactionRepository {

    @Autowired
    private DataSource<Transaction, List<Transaction>> dataSource;

    @Override
    public Transaction save(Transaction transaction) {
        this.dataSource.getData().stream().filter(t -> t.getId().equals(transaction.getId())).findAny().ifPresent(t -> {
            throw new TransactionAlreadyExistsException(String.format("Transaction with %s id already exists", t.getId()));
        });
        if (transaction.getParentId() != null) {
            this.dataSource.getData().stream().filter(t -> transaction.getParentId().equals(t.getId()))
                    .findAny().orElseThrow(() ->
                            new TransactionNotFoundException(String.format("Transaction with %s id does not exists", transaction.getParentId()))
                    );
        }

        this.dataSource.save(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findTransactionsByType(String type) {
        return this.dataSource.getData().stream().filter(t -> type.equals(t.getType())).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findAll() {
        return this.dataSource.getData();
    }

}
