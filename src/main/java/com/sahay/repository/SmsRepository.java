package com.sahay.repository;

import com.sahay.entity.Sms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Repository
public interface SmsRepository extends JpaRepository<Sms, Long> {

    @Query(value = "SELECT TOP (?2)  [id] ,[phone] , [message] FROM [mobile_banking].[dbo].[Sms] WHERE phone = ?1 ORDER BY request_date  DESC   ",
            nativeQuery = true)
    List<Sms> findTopMessagesByPhone(String phone, int limit);


//    SELECT [id]
//            ,[phone]
//            ,[message]
//            ,[txnref]
//            ,[status]
//            ,[request_date]
//            ,[message_id]
//            ,[processed_date]
//    FROM [mobile_banking].[dbo].[Sms] WHERE message LIKE '%Jigjiga teller 1%' AND request_date BETWEEN '2022-10-01' AND '2022-10-10'


    @Query(value = "SELECT  [id] ,[phone] , [message] , [request_date] FROM [mobile_banking].[dbo].[Sms] WHERE message LIKE %:name% AND request_date BETWEEN :startDate AND :endDate ",
            nativeQuery = true)
    List<Sms> findMessagesByTellerName(
            @Param("name") String name ,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate

    );
}
