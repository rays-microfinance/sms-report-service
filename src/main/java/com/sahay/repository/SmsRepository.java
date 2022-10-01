package com.sahay.repository;

import com.sahay.entity.Sms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.awt.print.Pageable;
import java.util.List;


@Repository
public interface SmsRepository  extends JpaRepository<Sms , Long> {

    @Query(value = "SELECT TOP (?2)  [id] ,[phone] , [message] FROM [mobile_banking].[dbo].[Sms] WHERE phone = ?1 ORDER BY request_date  DESC   " ,
            nativeQuery = true)
    List<Sms> findTop5ByPhone(String phone , int limit);
}
