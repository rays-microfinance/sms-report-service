package com.sahay.service;


import com.sahay.dto.CountResponse;
import com.sahay.dto.VolumeResponse;
import com.sahay.entity.Transaction;
import com.sahay.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class TransactionService {


    private final TransactionRepository transactionRepository;


    public List<Transaction> getBulkPaymentDepositHistory(String account) {
        List<Transaction> bulkPaymentTransactionHistory = transactionRepository.findBulkPaymentTransactionHistory(account);

        return bulkPaymentTransactionHistory;
    }

    // TODO: 10/17/2023 TRX VOLUMES AND COUNTS

    // SEND MONEY

    public VolumeResponse getSendMoneyVolume() {

        System.out.println(LocalDate.now());
        int sendMoneyVolume = transactionRepository.findSendMoneyVolume(getYesterday(), LocalDate.now());
        var response = new VolumeResponse();
        response.setType("SEND MONEY VOLUME");
        response.setVolume(sendMoneyVolume);
        log.info("VOLUME RESPONSE : {}", response);
        return response;
    }

    public CountResponse getSendMoneyCount() {
        int sendMoneyCount = transactionRepository.findSendMoneyCount(getYesterday(), LocalDate.now());
        var response = new CountResponse();
        response.setTransactionType("SEND MONEY COUNT");
        response.setCount(sendMoneyCount);
        log.info("VOLUME RESPONSE : {}", response);
        return response;
    }

    // AGENT WITHDRAWAL

    public VolumeResponse getAgentWithdrawalVolume() {
        int sendMoneyVolume = transactionRepository.findAgentWithdrawalVolume(getYesterday(), LocalDate.now());
        var response = new VolumeResponse();
        response.setType("AGENT WITHDRAWAL VOLUME");
        response.setVolume(sendMoneyVolume);
        log.info("VOLUME RESPONSE : {}", response);
        return response;
    }

    public CountResponse getAgentDepositCount() {
        int sendMoneyCount = transactionRepository.findAgentWithdrawalCount(getYesterday(), LocalDate.now());
        var response = new CountResponse();
        response.setTransactionType("AGENT WITHDRAWAL COUNT");
        response.setCount(sendMoneyCount);
        log.info("VOLUME RESPONSE : {}", response);
        return response;
    }

    // AGENT DEPOSIT

    public VolumeResponse getAgentDepositVolume() {
        int sendMoneyVolume = transactionRepository.findAgentDepositVolume(getYesterday(), LocalDate.now());
        var response = new VolumeResponse();
        response.setType("AGENT DEPOSIT VOLUME");
        response.setVolume(sendMoneyVolume);
        log.info("VOLUME RESPONSE : {}", response);
        return response;
    }

    public CountResponse getAgentDeepositCount() {
        int sendMoneyCount = transactionRepository.findAgentDepositCount(getYesterday(), LocalDate.now());
        var response = new CountResponse();
        response.setTransactionType("AGENT DEPOSIT COUNT");
        response.setCount(sendMoneyCount);
        log.info("VOLUME RESPONSE : {}", response);
        return response;
    }

    // BUY GOODS

    public VolumeResponse getBuyGoodsVolume() {
        int sendMoneyVolume = transactionRepository.findBuyGoodsVolume(getYesterday(), LocalDate.now());
        log.info("SEND MONEY VOLUME :{}", sendMoneyVolume);
        var response = new VolumeResponse();
        response.setType("BUY GOODS VOLUME");
        response.setVolume(sendMoneyVolume);
        log.info("VOLUME RESPONSE : {}", response);
        return response;
    }

    public CountResponse getButGoodsCount() {
        int sendMoneyCount = transactionRepository.findBuyGoodsCount(getYesterday(), LocalDate.now());
        log.info("SEND MONEY VOLUME :{}", sendMoneyCount);
        var response = new CountResponse();
        response.setTransactionType("BUY GOODS COUNT");
        response.setCount(sendMoneyCount);
        log.info("VOLUME RESPONSE : {}", response);
        return response;
    }

    // TELLER DEPOSIT

    public VolumeResponse getTellerDepositVolume() {
        int buyGoodsVolume = transactionRepository.findTellerDepositVolume(getYesterday(), LocalDate.now());
        var response = new VolumeResponse();
        response.setType("TELLER DEPOSIT VOLUME");
        response.setVolume(buyGoodsVolume);
        log.info("VOLUME RESPONSE : {}", response);
        return response;
    }

    public CountResponse getTellerDepositCount() {
        int buyGoodsCount = transactionRepository.findTellerDepositCount(getYesterday(), LocalDate.now());
        var response = new CountResponse();
        response.setTransactionType("TELLER DEPOSIT COUNT");
        response.setCount(buyGoodsCount);
        log.info("VOLUME RESPONSE : {}", response);
        return response;
    }

    // TELLER WITHDRAWAL
    public VolumeResponse getTellerWithdrawalVolume() {
        int tellerWithdrawalVolume = transactionRepository.findTellerWithdrawalVolume(getYesterday(), LocalDate.now());
        var response = new VolumeResponse();
        response.setType("TELLER WITHDRAWAL VOLUME");
        response.setVolume(tellerWithdrawalVolume);
        log.info("VOLUME RESPONSE : {}", response);
        return response;
    }

    public CountResponse getTellerWithdrawalCount() {
        int tellerWithdrawalCount = transactionRepository.findTellerWithdrawalCount(getYesterday(), LocalDate.now());
        log.info("SEND MONEY VOLUME :{}", tellerWithdrawalCount);
        var response = new CountResponse();
        response.setTransactionType("TELLER WITHDRAWAL COUNT");
        response.setCount(tellerWithdrawalCount);
        log.info("VOLUME RESPONSE : {}", response);
        return response;
    }

    // AIRTIME PURCHASE
    public VolumeResponse getAirtimeVolume() {
        int airtimePurchaseVolume = transactionRepository.findAirtimePurchaseVolume(getYesterday(), LocalDate.now());
        log.info("SEND MONEY VOLUME :{}", airtimePurchaseVolume);
        var response = new VolumeResponse();
        response.setType("AIRTIME PURCHASE VOLUME");
        response.setVolume(airtimePurchaseVolume);
        log.info("VOLUME RESPONSE : {}", response);
        return response;
    }

    public CountResponse getAirtimeCount() {
        int airtimePurchaseCount = transactionRepository.findAirtimePurchaseCount(getYesterday(), LocalDate.now());
        var response = new CountResponse();
        response.setTransactionType("AIRTIME PURCHASE COUNT");
        response.setCount(airtimePurchaseCount);
        log.info("VOLUME RESPONSE : {}", response);
        return response;
    }

    public LocalDate getYesterday() {
        LocalDate currentDate = LocalDate.now();
        // Calculate yesterday's date
        LocalDate yesterday = currentDate.minusDays(1);
        // Print yesterday's date
        System.out.println("Yesterday's date: " + yesterday);
        return yesterday;
    }

    private String formatNumber(long number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(number);
    }

    public String generateReport() {
        LocalDate yesterday = getYesterday();
        StringBuilder report = new StringBuilder();

        report.append("SAHAY DAILY REPORT\n====================\n");
        report.append("Date: " + yesterday + "\n\n");

        report.append(generateTransactionReport("SEND MONEY", getSendMoneyVolume(), getSendMoneyCount()));
        report.append(generateTransactionReport("BUY GOODS", getBuyGoodsVolume(), getButGoodsCount()));
        report.append(generateTransactionReport("AGENT WITHDRAWAL", getAgentWithdrawalVolume(), getAgentDepositCount()));
        report.append(generateTransactionReport("AGENT DEPOSIT", getAgentDepositVolume(), getAgentDeepositCount()));
        report.append(generateTransactionReport("TELLER WITHDRAWAL", getTellerWithdrawalVolume(), getTellerWithdrawalCount()));
        report.append(generateTransactionReport("TELLER DEPOSIT", getTellerDepositVolume(), getTellerDepositCount()));
        report.append(generateTransactionReport("AIRTIME PURCHASE", getAirtimeVolume(), getAirtimeCount()));

        return report.toString();
    }

    // Helper method to generate a transaction-specific report
    private String generateTransactionReport(String transactionType, VolumeResponse volume, CountResponse count) {
        StringBuilder transactionReport = new StringBuilder();

        transactionReport.append(transactionType + "\n\n");
        transactionReport.append("Volume : " + formatNumber(volume.getVolume()) + "\n");
        transactionReport.append("Count : " + formatNumber(count.getCount()) + "\n\n");

        return transactionReport.toString();
    }


}
