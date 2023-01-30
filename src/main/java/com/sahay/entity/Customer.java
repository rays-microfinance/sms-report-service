package com.sahay.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    private Long id;

    private String phoneNumber;

    private int tranLimitTypeId;
    private int dailyLimitTypeId;
    private int balLimitTypeId;


}
