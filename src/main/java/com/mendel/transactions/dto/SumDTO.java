package com.mendel.transactions.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class SumDTO implements Serializable {
    private Double sum;
}
