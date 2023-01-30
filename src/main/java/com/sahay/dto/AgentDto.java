package com.sahay.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentDto {

    private String response;
    private String phoneNumber;
    private String name;
    private String floatAccount;
    private String commissionAccount;
    private String region;
    private String village;
    private String district;
    private String zone;
}
