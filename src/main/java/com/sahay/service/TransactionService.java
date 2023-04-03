package com.sahay.service;


import com.sahay.entity.Transaction;
import com.sahay.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionService {


    private final TransactionRepository transactionRepository;

    public List<Transaction> getBulkPaymentDepositHistory(String account) {
        List<Transaction> bulkPaymentTransactionHistory = transactionRepository.findBulkPaymentTransactionHistory(account);

        return bulkPaymentTransactionHistory;
    }


}
