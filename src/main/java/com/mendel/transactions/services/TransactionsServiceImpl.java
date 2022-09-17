package com.mendel.transactions.services;

import com.mendel.transactions.dto.SumDTO;
import com.mendel.transactions.dto.TransactionDTO;
import com.mendel.transactions.dto.TransactionInputDTO;
import com.mendel.transactions.exceptions.TransactionNotFoundException;
import com.mendel.transactions.models.Transaction;
import com.mendel.transactions.repositories.transaction.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public TransactionDTO saveTransaction(Long transactionId, TransactionInputDTO transaction) {
        Transaction transactionModel = transaction.toModel();
        transactionModel.setId(transactionId);
        return TransactionDTO.fromModel(transactionRepository.save(transactionModel));
    }

    @Override
    public List<Long> findTransactionsIdsByType(String type) {
        return transactionRepository.findTransactionsByType(type).stream()
                .map(Transaction::getId)
                .collect(Collectors.toList());
    }

    @Override
    public SumDTO sumRelatedTransactionsAmounts(Long transactionId) {
        List<Transaction> transactionList = transactionRepository.findAll();
        Transaction root = transactionList.stream().filter(t -> transactionId.equals(t.getId())).findFirst().orElseThrow(() ->
                new TransactionNotFoundException(String.format("Transaction with %s id does not exists", transactionId))
        );
        Double transactionsAmount = sumRelatedTransactionsAmounts(root);
        return new SumDTO(transactionsAmount);
    }

    private Double sumRelatedTransactionsAmounts(Transaction root) {
        return sumRelatedTransactionsAmounts(root, root.getAmount());
    }

    private Double sumRelatedTransactionsAmounts(Transaction root, Double acc) {
        List<Transaction> children = getChildrenTransactions(root);
        if (children.isEmpty()) {
            return root.getAmount();
        } else {
            for (Transaction t : children) {
                acc += sumRelatedTransactionsAmounts(t, t.getAmount());
            }
        }
        return acc;
    }


    /**
     * Return a transaction children
     *
     * @param transaction
     * @return
     */
    private List<Transaction> getChildrenTransactions(Transaction transaction) {
        return transactionRepository.findAll()
                .stream()
                .filter(t -> transaction.getId().equals(t.getParentId())).collect(Collectors.toList());
    }
}
