package com.mendel.transactions.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.mendel.transactions.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInputDTO implements Serializable {
    @NotNull(message = "Amount is mandatory")
    private Double amount;
    @JsonProperty("parent_id")
    private Long parentId;
    @NotBlank(message = "Type is mandatory")
    private String type;

    public Transaction toModel(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Transaction.class);
    }
}
