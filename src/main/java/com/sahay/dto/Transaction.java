package com.sahay.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String TransactionId;
    private String Account;
    private Double Amount;
    private String Type;
    private String Narration;
    private Date TranDate;
    private Double RunningBalance;
}
