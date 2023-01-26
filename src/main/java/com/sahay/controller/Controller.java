package com.sahay.controller;


import com.sahay.dto.ErrorResponse;
import com.sahay.dto.LimitRequest;
import com.sahay.dto.SmsDto;
import com.sahay.entity.Sms;
import com.sahay.service.CustomerService;
import com.sahay.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.hibernate.validator.constraints.Range;
import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class Controller {

    private final SmsService smsService;

    private final CustomerService customerService;

    // TODO: all sms

    @GetMapping("/sms")
    public ResponseEntity<?> getMessage(
            @Valid
            @RequestParam("phone") String phone,
            @Range(message = "limit should be a number")
            @RequestParam(value = "limit", defaultValue = "5") int limit) throws Exception {

        if (phone.startsWith("0")) {

            String substring = phone.substring(1);
            phone = "251" + substring;

            log.debug("Request phone  : {} limit :{} ", phone, limit);

        }

        List<Sms> smsResponse = smsService.getMessage(phone, limit);
        SmsDto smsDto = new SmsDto();
        smsDto.setResponse("000");
        smsDto.setMessage(smsResponse);
        log.debug("Response inside getSms() : {}", smsResponse);
        return new ResponseEntity<>(smsDto, HttpStatus.OK);
    }

    // TODO: teller sms

    @GetMapping("/sms-teller")
    public ResponseEntity<?> getTellerMessages(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    )
            throws Exception {

        log.debug("teller name: {}", name);
        log.debug("phone number: {}", phone);
        log.debug("start date: {}", startDate);
        log.debug("end date: {}", endDate);
        List<Sms> smsResponse = smsService.getTellerMessages(name, phone, startDate, endDate);

        if (smsResponse.size() < 0) {

        }
        var smsDto = new SmsDto();
        smsDto.setResponse("000");
        smsDto.setMessage(smsResponse);
        log.debug("Response inside getTellerSms() : {}", smsResponse);
        return new ResponseEntity<>(smsDto, HttpStatus.OK);
    }

    // todo : customer limit

    @PostMapping("/customer-limit")
    public ResponseEntity<?> updateCustomerLimit(@RequestBody() LimitRequest request) {
        customerService.updateLimit(
                request.getPhoneNumber(),
                request.getTranLimitId(),
                request.getDailyLimitId(),
                request.getBalanceLimitId()
        );

        var response = new ErrorResponse();
        response.setResponse("000");
        response.setMessage("Customer limit is updated successfully!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
