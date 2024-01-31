package com.sahay.controller;


import com.sahay.dto.*;
import com.sahay.entity.Agent;
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
import org.springframework.http.MediaType;
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
        log.info("sms : {}" , smsResponse);
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

    //todo: get customer limit

    @GetMapping(value = "/customer-limit/{phoneNumber}" , produces = "application/json")
    public ResponseEntity<?> getCustomerLimit(@PathVariable(value = "phoneNumber") String phoneNumber) {

        var customerLimit = customerService.getCustomerLimit(phoneNumber);

        if (customerLimit == null) {
            var errorResponse = new ErrorResponse();
            errorResponse.setResponse("999");
            errorResponse.setMessage("Customer not found");

            return new ResponseEntity<>(errorResponse, HttpStatus.OK);

        }

        var limit = new JSONObject(customerLimit);

        System.out.println("limit = " + limit);

        var response = new JSONObject();

        response.put("phoneNumber", limit.getString("phoneNumber"));
        response.put("tranLimitTypeId", limit.getInt("tranLimitTypeId"));

        response.put("dailyLimitTypeId", limit.getInt("dailyLimitTypeId"));
        response.put("balLimitTypeId", limit.getInt("balLimitTypeId"));
        response.put("response", "000");

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);

    }


    // todo : update customer limit

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

    // TODO: agent info

    @GetMapping("/agent/{code}")
    public ResponseEntity<?> getAgentInfo(@PathVariable("code") String code) throws Exception {

        Agent agentInfo = customerService.getAgentInfo(code);


        if (agentInfo == null) {
            var errorResponse = new ErrorResponse();
            errorResponse.setResponse("999");
            errorResponse.setMessage("Agent not found");

            return new ResponseEntity<>(errorResponse, HttpStatus.OK);

        }

        var agentResponse = new AgentDto();
        JSONObject response = new JSONObject(agentInfo);

        System.out.println("response = " + response);
        agentResponse.setResponse("000");
        agentResponse.setName(response.getString("name"));
        agentResponse.setPhoneNumber(response.getString("phoneNumber"));
        agentResponse.setFloatAccount(response.getString("floatAccount"));
        agentResponse.setRegion(response.getString("region"));
        agentResponse.setZone(response.getString("zone"));
        agentResponse.setDistrict(response.getString("district"));
        agentResponse.setVillage(response.getString("village"));
        agentResponse.setCommissionAccount(response.getString("commissionAccount"));

        return new ResponseEntity<>(agentResponse, HttpStatus.OK);
    }

    // sms for portal

    @GetMapping("/sms/portal")
    public ResponseEntity<?> getMessagesRange(
            @Valid
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws Exception {

        if (phoneNumber.startsWith("0")) {

            String substring = phoneNumber.substring(1);
            phoneNumber = "251" + substring;

            log.debug("Request phone  : {} startDate :{} : endDate : {} ", phoneNumber, startDate , endDate);

        }

        List<Sms> smsResponse = smsService.getSmsForPortal(phoneNumber, startDate , endDate);
        log.info("sms : {}" , smsResponse);
        SmsDto smsDto = new SmsDto();
        smsDto.setResponse("000");
        smsDto.setMessage(smsResponse);
        log.debug("Response inside getSms() : {}", smsResponse);
        return new ResponseEntity<>(smsDto, HttpStatus.OK);
    }

    // statement

    @GetMapping("/statement/old")
    public ResponseEntity<StatementDto> getOldStatement(
            @RequestParam String phoneNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        try {
            StatementDto customerDetails = smsService.getOldAccountStatement(phoneNumber, startDate, endDate);

            log.info("STATEMENT : {}" , customerDetails);

            if (customerDetails != null) {
                return new ResponseEntity<>(customerDetails, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("ERROR OCCURED : {}" , e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // new statement
    @GetMapping("/statement/new")
    public ResponseEntity<StatementDto> getNewStatement(
            @RequestParam String phoneNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        try {
            StatementDto customerDetails = smsService.getNewAccountStatement(phoneNumber, startDate, endDate);

            log.info("STATEMENT : {}" , customerDetails);

            if (customerDetails != null) {
                return new ResponseEntity<>(customerDetails, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("ERROR OCCURED : {}" , e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
