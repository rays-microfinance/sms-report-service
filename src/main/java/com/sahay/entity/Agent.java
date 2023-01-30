package com.sahay.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Agent {

    @Id
    @Column(name = "PhoneNumber")
    private String phoneNumber;
    private String name;
    @Column(name = "agentCode")
    private String floatAccount;
    private String commissionAccount;
    private String region;
    private String village;
    private String district;
    private String zone;

}
