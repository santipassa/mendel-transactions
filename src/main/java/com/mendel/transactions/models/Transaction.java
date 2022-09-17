package com.mendel.transactions.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Model{
    private Long id;
    private Double amount;
    private Long parentId;
    private String type;
}
