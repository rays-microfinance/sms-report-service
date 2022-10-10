package com.sahay.controller;


import com.sahay.dto.SmsDto;
import com.sahay.entity.Sms;
import com.sahay.service.SmsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class SmsController {

    private final SmsService smsService;


    @GetMapping("/sms")
    public ResponseEntity<?> getMessage(
            @Valid
            @RequestParam("phone") String phone,
            @Range(message = "limit should be a number")
            @RequestParam(value = "limit", defaultValue = "5") int limit) throws Exception {

        if (phone.startsWith("0")) {

            String substring = phone.substring(1);
            String formatPhone = "251" + substring;
            phone = formatPhone;

            log.debug("Request phone  : {} limit :{} ", phone, limit);

        }

        List<Sms> smsResponse = smsService.getMessage(phone, limit);
        SmsDto smsDto = new SmsDto();
        smsDto.setResponse("000");
        smsDto.setMessage(smsResponse);
        log.debug("Response inside getSms() : {}", smsResponse);
        return new ResponseEntity<SmsDto>(smsDto, HttpStatus.OK);
    }

    @GetMapping("/sms-teller")
    public ResponseEntity<?> getTellerMessages(
            @RequestParam("name") String name,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    )
            throws Exception {

        log.debug("teller name: {}", name);
        log.debug("start date: {}", startDate);
        log.debug("end date: {}", endDate);


        List<Sms> smsResponse = smsService.getTellerMessages(name, startDate, endDate);
        SmsDto smsDto = new SmsDto();
        smsDto.setResponse("000");
        smsDto.setMessage(smsResponse);
        log.debug("Response inside getTellerSms() : {}", smsResponse);
        return new ResponseEntity<SmsDto>(smsDto, HttpStatus.OK);
    }
}
