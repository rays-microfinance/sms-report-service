package com.sahay.controller;

import com.sahay.dto.CustomResponse;
import com.sahay.dto.SlackRequest;
import com.sahay.service.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/slack")
@RequiredArgsConstructor
public class SlackController {

    private final SlackService slackService;

    @PostMapping
    public ResponseEntity<?> sendToSlack(@RequestBody SlackRequest request) {
        ResponseEntity<String> stringResponseEntity = slackService.sendToSlack(request.getMessage(), request.getUrl());
        var response = new CustomResponse();
        if (!stringResponseEntity.getBody().equals("ok")) {
            response.setStatus("400");
            response.setMessage("Failed to send to slack ! " + stringResponseEntity.getBody());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            response.setStatus("200");
            response.setMessage("Message successfully sent");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
