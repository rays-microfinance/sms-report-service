package com.sahay.repository;

import com.sahay.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    @Query(value = "SELECT  " +
            "[Id]"+
            "      ,[TransactionId]" +
            "      ,[Account]" +
            "      ,[Amount]" +
            "      ,[Narration]" +
            "      ,[Channel]" +
            "      ,[TranDate]" +
            "  FROM [mobile_banking].[dbo].[Transaction] where Account = ?1" +
            "  and TranTypeId = 1 and IsReversed = 0 ORDER BY TranDate DESC", nativeQuery = true)
    List<Transaction> findBulkPaymentTransactionHistory(String account);
}
