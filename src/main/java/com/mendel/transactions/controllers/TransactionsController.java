package com.mendel.transactions.controllers;

import com.mendel.transactions.dto.SumDTO;
import com.mendel.transactions.dto.TransactionDTO;
import com.mendel.transactions.dto.TransactionInputDTO;
import com.mendel.transactions.services.TransactionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller to do some operations with Mendel Transactions
 */
@Validated
@RestController
@RequestMapping("/transactions")
@Slf4j
public class TransactionsController {

    @Autowired
    TransactionsService transactionService;

    /**
     * Create Transaction
     */
    @PutMapping(value = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TransactionDTO> createTransaction(@PathVariable("transactionId") Long transactionId, @Valid @RequestBody TransactionInputDTO transactionDTO) {
        log.info(String.format("Creating transaction with id: %s", transactionId));
        return new ResponseEntity<>(transactionService.saveTransaction(transactionId, transactionDTO), HttpStatus.CREATED);

    }

    /**
     * Get Transactions id by Type
     */
    @GetMapping(value = "/types/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Long>> getTransactionsIdByType(@PathVariable("type") String type) {
        log.info(String.format("Getting transactions ids with type: %s", type));
        return ResponseEntity.ok(transactionService.findTransactionsIdsByType(type));
    }

    /**
     * Get transactions sum amount by related parents_id
     */
    @GetMapping(value = "/sum/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<SumDTO> getTransactionsSumAmountByParent(@PathVariable("transactionId") Long transactionId) {
        log.info(String.format("Getting transactions sum amount from: %s", transactionId));
        return ResponseEntity.ok(transactionService.sumRelatedTransactionsAmounts(transactionId));
    }
}
