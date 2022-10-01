package com.sahay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SmsReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsReportServiceApplication.class, args);
    }

}
