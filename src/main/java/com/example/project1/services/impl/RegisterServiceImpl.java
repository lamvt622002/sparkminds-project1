package com.example.project1.services.impl;

import com.example.project1.entities.Customer;
import com.example.project1.entities.User;
import com.example.project1.mapper.CustomerMapper;
import com.example.project1.payload.request.RegisterRequest;
import com.example.project1.repository.CustomerRepository;
import com.example.project1.services.SendingEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements com.example.project1.services.RegisterService {
    private final CustomerMapper customerMapper;

    private final CustomerRepository customerRepository;

    private final SendingEmailService sendingEmailService;

    @Override
    @Transactional
    public void register(RegisterRequest registerRequest) {
        Customer customer = customerMapper.registerRequestToCustomer(registerRequest);

        User userSaved = customerRepository.save(customer);

        sendingEmailService.sendVerificationEmail(userSaved);
    }
}
