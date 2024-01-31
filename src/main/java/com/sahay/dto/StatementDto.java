package com.sahay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementDto {

    private Customer customer;
    private List<Transaction> transactions;

    // Constructors, getters, and setters
    // You can generate these using your IDE or write them manually


}




