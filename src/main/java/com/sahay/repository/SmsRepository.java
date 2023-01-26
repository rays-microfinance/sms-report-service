package com.sahay.repository;

import com.sahay.entity.Sms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface SmsRepository extends JpaRepository<Sms, Long> {

    @Query(value = "SELECT TOP (?2)  [id] ,[phone] , [message] , [request_date]  FROM [mobile_banking].[dbo].[Sms] WHERE phone = ?1 ORDER BY request_date  DESC  ",
            nativeQuery = true)
    List<Sms> findTopMessagesByPhone(String phone, int limit);


    @Query(value = "SELECT  [id] ,[phone] , [message] , [request_date] FROM [mobile_banking].[dbo].[Sms] WHERE message LIKE %:name% AND phone = :phone AND request_date BETWEEN :startDate AND :endDate ",
            nativeQuery = true)
    List<Sms> findMessagesByTellerName(
            @Param("name") String name,
            @Param("phone") String phone,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}



