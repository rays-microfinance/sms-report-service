package com.sahay.repository;

import com.sahay.entity.Customer;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import javax.validation.Payload;

@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    @Query(value = "SELECT [Id], [PhoneNumber] ,[TranLimitTypeId],[DailyLimitTypeId],[BalLimitTypeId] FROM [mobile_banking].[dbo].[Customer] WHERE PhoneNumber = ?1", nativeQuery = true)
    Customer getCustomerLimit(String phoneNumber);

    @Modifying
    @Query(value = "UPDATE [mobile_banking].[dbo].[Customer] SET  TranLimitTypeId = ?2, DailyLimitTypeId = ?3 , BalLimitTypeId = ?4 WHERE PhoneNumber =?1", nativeQuery = true)
    void updateCustomerLimit(String phone, int tranLimitId, int dailyLimitId, int balanceLimitId);


}
