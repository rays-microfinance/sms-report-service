package com.sahay;

import com.sahay.dto.CustomResponse;
import com.sahay.dto.SlackRequest;
import com.sahay.service.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor

@EnableAsync
public class SmsReportServiceApplication implements ApplicationRunner {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(SmsReportServiceApplication.class, args);
    }


    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:sqlserver://172.16.1.15:53500;databaseName=mobile_banking;trustServerCertificate=true;encrypt=true")
                .username("sa")
                .password("@R@y5#MFI!!2020_sahay")
                .driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
                .build();
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
