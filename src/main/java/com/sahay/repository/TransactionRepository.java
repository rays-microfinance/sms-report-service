package com.sahay.repository;

import com.sahay.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    @Query("SELECT t.id, t.transactionId, t.account, t.amount, t.narration, t.channel, t.tranDate FROM Transaction t WHERE t.account = :account AND t.tranTypeId = 1 AND t.isReversed = 0 ORDER BY t.tranDate DESC")
    List<Transaction> findBulkPaymentTransactionHistory(@Param("account") String account);


    // SEND MONEY
    @Query(value = "SELECT SUM(Amount) " +
            "FROM [mobile_banking].[dbo].[Transaction]  " +
            "WHERE TransactionReqType = 'SEM' " +
            "AND TranTypeId = 0" +
            "AND Narration NOT LIKE '%charge%' " +
            "AND Narration NOT LIKE '%tax%' " +
            "AND Narration NOT LIKE '%commission%' " +
            "AND TranDate BETWEEN :startDate AND :endDate", nativeQuery = true)
    int findSendMoneyVolume(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query(value = "SELECT COUNT(*) " +
            "FROM [mobile_banking].[dbo].[Transaction]  " +
            "WHERE TransactionReqType = 'SEM' " +
            "AND TranTypeId = 0" +
            "AND Narration NOT LIKE '%charge%' " +
            "AND Narration NOT LIKE '%tax%' " +
            "AND Narration NOT LIKE '%commission%' " +
            "AND TranDate BETWEEN :startDate AND :endDate", nativeQuery = true)
    int findSendMoneyCount(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    // AGENT WITHDRAWAL

    @Query(value = "SELECT SUM(Amount) " +
            "FROM [mobile_banking].[dbo].[Transaction]  " +
            "WHERE TransactionReqType = 'WIT' " +
            "AND TranTypeId = 0" +
            "AND Narration NOT LIKE '%charge%' " +
            "AND Narration NOT LIKE '%tax%' " +
            "AND Narration NOT LIKE '%commission%' " +
            "AND TranDate BETWEEN :startDate AND :endDate", nativeQuery = true)
    int findAgentWithdrawalVolume(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query(value = "SELECT COUNT(*) " +
            "FROM [mobile_banking].[dbo].[Transaction]  " +
            "WHERE TransactionReqType = 'WIT' " +
            "AND TranTypeId = 0" +
            "AND Narration NOT LIKE '%charge%' " +
            "AND Narration NOT LIKE '%tax%' " +
            "AND Narration NOT LIKE '%commission%' " +
            "AND TranDate BETWEEN :startDate AND :endDate", nativeQuery = true)
    int findAgentWithdrawalCount(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    // AGENT DEPOSIT

    @Query(value = "SELECT SUM(Amount) " +
            "FROM [mobile_banking].[dbo].[Transaction]  " +
            "WHERE TransactionReqType = 'DEP' " +
            "AND TranTypeId = 1" +
            "AND Narration NOT LIKE '%charge%' " +
            "AND Narration NOT LIKE '%tax%' " +
            "AND Narration NOT LIKE '%commission%' " +
            "AND TranDate BETWEEN :startDate AND :endDate", nativeQuery = true)
    int findAgentDepositVolume(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query(value = "SELECT COUNT(*) " +
            "FROM [mobile_banking].[dbo].[Transaction]  " +
            "WHERE TransactionReqType = 'DEP' " +
            "AND TranTypeId = 1" +
            "AND Narration NOT LIKE '%charge%' " +
            "AND Narration NOT LIKE '%tax%' " +
            "AND Narration NOT LIKE '%commission%' " +
            "AND TranDate BETWEEN :startDate AND :endDate", nativeQuery = true)
    int findAgentDepositCount(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    // BUY GOODS

    @Query(value = "SELECT SUM(Amount) " +
            "FROM [mobile_banking].[dbo].[Transaction]  " +
            "WHERE TransactionReqType = 'BUG' " +
            "AND TranTypeId = 0" +
            "AND Narration NOT LIKE '%charge%' " +
            "AND Narration NOT LIKE '%tax%' " +
            "AND Narration NOT LIKE '%commission%' " +
            "AND TranDate BETWEEN :startDate AND :endDate", nativeQuery = true)
    int findBuyGoodsVolume(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT COUNT(*) " +
            "FROM [mobile_banking].[dbo].[Transaction]  " +
            "WHERE TransactionReqType = 'BUG' " +
            "AND TranTypeId = 0" +
            "AND Narration NOT LIKE '%charge%' " +
            "AND Narration NOT LIKE '%tax%' " +
            "AND Narration NOT LIKE '%commission%' " +
            "AND TranDate BETWEEN :startDate AND :endDate", nativeQuery = true)
    int findBuyGoodsCount(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    // TELLER DEPOSIT
    @Query(value = "SELECT SUM(Amount) " +
            "FROM [mobile_banking].[dbo].[Transaction]  " +
            "WHERE TransactionReqType = 'TPO' " +
            "AND TranTypeId = 1" +
            "AND Narration NOT LIKE '%charge%' " +
            "AND Narration NOT LIKE '%tax%' " +
            "AND Narration NOT LIKE '%commission%' " +
            "AND TranDate BETWEEN :startDate AND :endDate", nativeQuery = true)
    int findTellerDepositVolume(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query(value = "SELECT COUNT(*) " +
            "FROM [mobile_banking].[dbo].[Transaction]  " +
            "WHERE TransactionReqType = 'TPO' " +
            "AND TranTypeId = 1" +
            "AND Narration NOT LIKE '%charge%' " +
            "AND Narration NOT LIKE '%tax%' " +
            "AND Narration NOT LIKE '%commission%' " +
            "AND TranDate BETWEEN :startDate AND :endDate", nativeQuery = true)
    int findTellerDepositCount(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);



    // TELLER WITHDRAWAL

    @Query(value = "SELECT SUM(Amount) " +
            "FROM [mobile_banking].[dbo].[Transaction]  " +
            "WHERE TransactionReqType = 'TPO' " +
            "AND TranTypeId = 0" +
            "AND Narration NOT LIKE '%charge%' " +
            "AND Narration NOT LIKE '%tax%' " +
            "AND Narration NOT LIKE '%commission%' " +
            "AND TranDate BETWEEN :startDate AND :endDate", nativeQuery = true)
    int findTellerWithdrawalVolume(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query(value = "SELECT COUNT(*) " +
            "FROM [mobile_banking].[dbo].[Transaction]  " +
            "WHERE TransactionReqType = 'TPO' " +
            "AND TranTypeId = 0" +
            "AND Narration NOT LIKE '%charge%' " +
            "AND Narration NOT LIKE '%tax%' " +
            "AND Narration NOT LIKE '%commission%' " +
            "AND TranDate BETWEEN :startDate AND :endDate", nativeQuery = true)
    int findTellerWithdrawalCount(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // AIRTIME PURCHASE

    @Query(value = "SELECT SUM(Amount) " +
            "FROM [mobile_banking].[dbo].[Transaction]  " +
            " where TransactionReqType IN ('ATTC', 'ATTA', 'ATTM')" +
            "AND TranTypeId = 0" +
            "AND Narration NOT LIKE '%charge%' " +
            "AND Narration NOT LIKE '%tax%' " +
            "AND Narration NOT LIKE '%commission%' " +
            "AND TranDate BETWEEN :startDate AND :endDate", nativeQuery = true)
    int findAirtimePurchaseVolume(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT COUNT(*) " +
            "FROM [mobile_banking].[dbo].[Transaction]  " +
            " where TransactionReqType IN ('ATTC', 'ATTA', 'ATTM')" +
            "AND TranTypeId = 0" +
            "AND Narration NOT LIKE '%charge%' " +
            "AND Narration NOT LIKE '%tax%' " +
            "AND Narration NOT LIKE '%commission%' " +
            "AND TranDate BETWEEN :startDate AND :endDate", nativeQuery = true)
    int findAirtimePurchaseCount(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}
