package com.sahay.controller;


import com.sahay.entity.Sms;
import com.sahay.service.SmsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
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

        if(phone.startsWith("0")){

            String substring = phone.substring(1);
            String formatPhone = "251" + substring;
            phone = formatPhone ;

            log.debug("Request phone  : {} limit :{} " , phone , limit );

        }

        List<Sms> smsResponse = smsService.getMessage(phone, limit);
        log.debug("Response inside getSms() : {}", smsResponse);
        return smsResponse;
    }
}
