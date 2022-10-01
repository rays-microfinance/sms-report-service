package com.sahay.controller;


import com.sahay.entity.Sms;
import com.sahay.service.SmsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/sms")
@Slf4j
public class SmsController {

    private final SmsService smsService;


    @GetMapping()
    public List<Sms> getMessage(
            @Valid
            @RequestParam("phone") String phone ,
            @Range( message = "limit should be a number")
            @RequestParam(value = "limit", defaultValue = "5") int limit) throws Exception {

        log.debug("Request phone  : {} limit :{} " , phone , limit );

        if(phone.startsWith("0")){
            String replace = phone.replace("0", "251");
            phone = replace;
        }

        List<Sms> smsResponse = smsService.getMessage(phone, limit);
        log.debug("Response inside getSms() : {}", smsResponse);
        return smsResponse;
    }
}
