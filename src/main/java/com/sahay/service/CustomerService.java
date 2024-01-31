package com.sahay.service;


import com.sahay.entity.Agent;
import com.sahay.entity.Customer;
import com.sahay.repository.AgentRepository;
import com.sahay.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final AgentRepository agentRepository;
    private final CustomerRepository customerRepository;


    public Customer getCustomerLimit(String phoneNumber) {
        return customerRepository.getCustomerLimit(phoneNumber);
    }

    public void updateLimit(String phone, int tranLimitId, int dailyLimitId, int balanceLimitId) {
        customerRepository.updateCustomerLimit(phone, tranLimitId, dailyLimitId, balanceLimitId);
    }


    public Agent getAgentInfo(String code) throws Exception {
        Agent agentByCode = agentRepository.getAgentByCode(code);

        return agentByCode;
    }
}
