package com.mendel.transactions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mendel.transactions.models.Transaction;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.io.Serializable;

@Data
public class TransactionDTO implements Serializable {
    private Long id;
    private Double amount;
    @JsonProperty("parent_id")
    private Long parentId;
    private String type;

    public static TransactionDTO fromModel(Transaction transaction){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    public Transaction toModel(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Transaction.class);
    }
}
