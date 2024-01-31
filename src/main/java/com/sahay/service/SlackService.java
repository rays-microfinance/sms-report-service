package com.sahay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackService {

    @Value("${slack.web-hook-url}")
    private String WEBHOOK_URL;

    private final RestTemplate restTemplate;

    private final TransactionService transactionService;


    @Scheduled(cron = "0 30 08 * * *")
    public void triggerTheReport() {
        String report = transactionService.generateReport();
        log.info("REPORT : {}", report);
        sendToSlack(report, WEBHOOK_URL);
    }

    public ResponseEntity<String> sendToSlack(String message, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String payload = "{\"text\": \"" + "<!channel>" + message + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(payload, headers);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, entity, String.class);
        return stringResponseEntity;
    }


}
