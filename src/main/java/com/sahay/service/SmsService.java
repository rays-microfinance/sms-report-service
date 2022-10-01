package com.sahay.service;


import com.sahay.entity.Sms;
import com.sahay.exception.MessageNotFoundException;
import com.sahay.repository.SmsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class SmsService {

    private final SmsRepository smsRepository;


    @Cacheable("Sms")
    public List<Sms> getMessage(String phone , int limit ) throws Exception{

        List<Sms> smsResponse = smsRepository.findTop5ByPhone(phone , limit );

        if (smsResponse.isEmpty()) {
            // throw not found exception
            log.error("Error message not found or phone number is not registered : {}", smsResponse);
            throw new MessageNotFoundException("No messages found or phone number doesnt exist");
        }

         return smsResponse;

    }

}
