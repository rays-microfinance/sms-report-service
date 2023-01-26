package com.sahay.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LimitRequest {

    private String phoneNumber;
    private int tranLimitId;
    private int dailyLimitId;
    private int balanceLimitId;
}
