package com.sahay.controller;


import com.sahay.entity.Transaction;
import com.sahay.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping(value = "bulk-deposit-history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchDepositHistory(@RequestParam("account") String account) {

        var customResponse = new JSONObject();

        List<Transaction> bulkPaymentDepositHistory = transactionService.getBulkPaymentDepositHistory(account);

        log.info("bulk payment deposit history", bulkPaymentDepositHistory);

        if (bulkPaymentDepositHistory.isEmpty()) {
            customResponse.put("response", "999");
            customResponse.put("responseDescription", "bulk payment account does not exist");
            return new ResponseEntity<>(customResponse.toString(), HttpStatus.OK);
        }

        customResponse.put("response", "000");
        customResponse.put("responseDescription", "successful");
        customResponse.put("transaction", bulkPaymentDepositHistory);
        return new ResponseEntity<>(customResponse.toString(), HttpStatus.OK);


    }
}
