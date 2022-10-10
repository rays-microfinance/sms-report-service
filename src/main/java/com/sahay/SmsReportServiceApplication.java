package com.sahay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootApplication
public class SmsReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsReportServiceApplication.class, args);

    }



}
