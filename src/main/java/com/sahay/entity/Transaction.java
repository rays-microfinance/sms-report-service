package com.sahay.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Transaction")
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Column(name = "TransactionId")
    private String transactionId;
    @Column(name = "TranTypeId")
    private int tranTypeId;
    @Column(name = "IsReversed")
    private int isReversed;
    private String account;
    private int amount;
    private String narration;
    private String channel;
    @Column(name = "TranDate")
    private String tranDate;
    @Column(name = "TransactionReqType")
    private String transactionReqType;


}
