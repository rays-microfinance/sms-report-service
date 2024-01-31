package com.sahay.service;


import com.sahay.dto.Customer;
import com.sahay.dto.StatementDto;
import com.sahay.dto.Transaction;
import com.sahay.entity.Sms;
import com.sahay.exception.MessageNotFoundException;
import com.sahay.repository.SmsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService {

    private final SmsRepository smsRepository;

    private final JdbcTemplate jdbcTemplate;

//    public List<Sms> getMessage(String phone, int limit) throws Exception {
//
//        List<Sms> smsResponse = smsRepository.findTopMessagesByPhone(phone, limit);
//
//        if (smsResponse.isEmpty()) {
//            // throw not found exception
//            log.error("Error message not found or phone number is not registered : {}", smsResponse);
//            throw new MessageNotFoundException("No messages found or phone number doesnt exist");
//        }
//        return smsResponse;
//    }

    public List<Sms> getTellerMessages(String name, String phone, LocalDate startDate, LocalDate endDate) throws Exception {

        List tellerMessages = smsRepository.findMessagesByTellerName(name, phone, startDate, endDate);

        if (tellerMessages.isEmpty()) {
            log.error("Teller with  : {}", name, "not found");
            throw new MessageNotFoundException("No messages found or invalid teller name");
        }
        return tellerMessages;
    }

    // GET SMS FOR SLACK TOOLS

    public List<Sms> getMessage(String phone, int limit) throws Exception {
        String procedureCall = "{CALL GetCustomerSms(?, ?)}";

        List<Sms> smsResponse = jdbcTemplate.query(
                procedureCall,
                new Object[]{phone, limit},
                (rs, rowNum) -> {
                    Sms sms = new Sms();
//                    sms.setId(rs.getLong("id"));
                    sms.setMessage(rs.getString("message"));
                    log.info("SMS : {}", sms);
                    StatementDto dto = new StatementDto();
                    // Map other fields as needed
                    return sms;
                }
        );

        if (smsResponse.isEmpty()) {
            // throw not found exception
            throw new MessageNotFoundException("No messages found or phone number doesn't exist");
        }
        return smsResponse;
    }

    // GET SMS FOR PORTAL

    public List<Sms> getSmsForPortal(String phone, LocalDate startDate, LocalDate endDate) throws Exception {
        String procedureCall = "{CALL GetMessageByPhone(?, ?, ?)}";

        log.info("REQUEST : {}", phone);
        List<Sms> smsResponse = jdbcTemplate.query(
                procedureCall,
                new Object[]{phone, startDate, endDate},
                (rs, rowNum) -> {
                    Sms sms = new Sms();
//                    sms.setId(rs.getLong("id"));
                    sms.setMessage(rs.getString("message"));
                    sms.setDate(rs.getDate("request_date"));
                    log.info("SMS : {}", sms);
                    StatementDto dto = new StatementDto();
                    // Map other fields as needed
                    return sms;
                }
        );

        if (smsResponse.isEmpty()) {
            // throw not found exception
            throw new MessageNotFoundException("No messages found or phone number doesn't exist");
        }
        return smsResponse;
    }


    // GET MINISTATMENT FOR PORTAL

    // new statement GetTransactionDetailsByAccountAndDateRange

    public StatementDto getNewAccountStatement(String phoneNumber, LocalDate startDate, LocalDate endDate) {
        String accountProcedureCall = "{CALL GetAccountDetailsFromAccountLinking(?)}";
        String transactionProcedureCall = "{CALL GetTransactionDetailsByAccountAndDateRange(?, ?, ?)}";

        Customer customer = new Customer();

        List<Customer> accountDetails = jdbcTemplate.query(
                accountProcedureCall,
                new Object[]{phoneNumber},
                (rs, rowNum) -> {
                    // Map account details from ResultSet
                    customer.setName(rs.getString("Name"));
                    return customer;
                }
        );
        log.info("ACCOUNT PART : {}", accountDetails);
        List<Transaction> transactionDetails = jdbcTemplate.query(
                transactionProcedureCall,
                new Object[]{phoneNumber, startDate, endDate}, // Replace with actual dates
                (rs, rowNum) -> {
                    Transaction transaction = new Transaction();

                    transaction.setTransactionId(rs.getString("TransactionId"));
                    transaction.setAccount(rs.getString("Account"));
                    transaction.setType(rs.getString("TransactionType"));
                    transaction.setAmount(rs.getDouble("Amount"));
                    transaction.setNarration(rs.getString("Narration"));
                    transaction.setTranDate(rs.getDate("TranDate"));
                    transaction.setRunningBalance(rs.getDouble("RunningBalance"));
                    return transaction;
                }
        );

        log.info("TRANSACTION PART : {}", transactionDetails);
        StatementDto customerDTO = new StatementDto();
        customer.setPhoneNumber(phoneNumber);
        customer.setName(customer.getName());

        customerDTO.setCustomer(customer);
        // Convert TransactionDetails to Transaction and add to transactions list
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : transactionDetails) {
            Transaction newTransaction = new Transaction();
            newTransaction.setTransactionId(transaction.getTransactionId());
            newTransaction.setAccount(transaction.getAccount());
            newTransaction.setAmount(transaction.getAmount());
            newTransaction.setType(transaction.getType());
            newTransaction.setNarration(transaction.getNarration());
            newTransaction.setTranDate(transaction.getTranDate());
            newTransaction.setRunningBalance(transaction.getRunningBalance());
            transactions.add(newTransaction);
        }
        customerDTO.setTransactions(transactions);

        return customerDTO;
    }


    // old statement


    public StatementDto getOldAccountStatement(String phoneNumber, LocalDate startDate, LocalDate endDate) {
        String accountProcedureCall = "{CALL GetAccountDetailsFromAccountLinking(?)}";
        String transactionProcedureCall = "{CALL GetTransactionDetailsByAccountAndDateRangeArchived(?, ?, ?)}";

        Customer customer = new Customer();

        List<Customer> accountDetails = jdbcTemplate.query(
                accountProcedureCall,
                new Object[]{phoneNumber},
                (rs, rowNum) -> {
                    // Map account details from ResultSet
                    customer.setName(rs.getString("Name"));
                    return customer;
                }
        );

        log.info("ACCOUNT PART : {}", accountDetails);

        List<Transaction> transactionDetails = jdbcTemplate.query(
                transactionProcedureCall,
                new Object[]{phoneNumber, startDate, endDate}, // Replace with actual dates
                (rs, rowNum) -> {
                    Transaction transaction = new Transaction();

                    transaction.setTransactionId(rs.getString("TransactionId"));
                    transaction.setAccount(rs.getString("Account"));
                    transaction.setType(rs.getString("TransactionType"));
                    transaction.setAmount(rs.getDouble("Amount"));
                    transaction.setNarration(rs.getString("Narration"));
                    transaction.setTranDate(rs.getDate("TranDate"));
                    transaction.setRunningBalance(rs.getDouble("RunningBalance"));
                    return transaction;
                }
        );

        log.info("TRANSACTION PART : {}", transactionDetails);
        StatementDto customerDTO = new StatementDto();
        customer.setPhoneNumber(phoneNumber);
        customer.setName(customer.getName());

        customerDTO.setCustomer(customer);
        // Convert TransactionDetails to Transaction and add to transactions list
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : transactionDetails) {
            Transaction newTransaction = new Transaction();
            newTransaction.setTransactionId(transaction.getTransactionId());
            newTransaction.setAccount(transaction.getAccount());
            newTransaction.setAmount(transaction.getAmount());
            newTransaction.setType(transaction.getType());
            newTransaction.setNarration(transaction.getNarration());
            newTransaction.setTranDate(transaction.getTranDate());
            newTransaction.setRunningBalance(transaction.getRunningBalance());
            transactions.add(newTransaction);
        }
        customerDTO.setTransactions(transactions);

        return customerDTO;
    }
}

