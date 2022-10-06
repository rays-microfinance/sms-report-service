package com.sahay.service;


import com.sahay.entity.Sms;
import com.sahay.exception.MessageNotFoundException;
import com.sahay.repository.SmsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService {

    private final SmsRepository smsRepository;


    public List<Sms> getMessage(String phone, int limit) throws Exception {

        List<Sms> smsResponse = smsRepository.findTopMessagesByPhone(phone, limit);

        if (smsResponse.isEmpty()) {
            // throw not found exception
            log.error("Error message not found or phone number is not registered : {}", smsResponse);
            throw new MessageNotFoundException("No messages found or phone number doesnt exist");
        }

        return smsResponse;

    }

}
