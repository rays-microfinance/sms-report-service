package com.sahay.service;


import com.sahay.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void updateLimit(String phone , int tranLimitId, int dailyLimitId, int balanceLimitId) {
        customerRepository.updateCustomerLimit(phone ,tranLimitId, dailyLimitId, balanceLimitId);
    }
}
