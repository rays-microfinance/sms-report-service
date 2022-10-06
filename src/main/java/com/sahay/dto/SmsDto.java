package com.sahay.dto;


import com.sahay.entity.Sms;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SmsDto {

    private String response;
    private List<Sms> message;
}
