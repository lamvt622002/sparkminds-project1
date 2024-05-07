package com.example.project1.mapper;

import com.example.project1.entities.Customer;
import com.example.project1.entities.User;
import com.example.project1.payload.request.RegisterRequest;
import com.example.project1.payload.response.LoginResponse;

public interface CustomerMapper {
    Customer registerRequestToCustomer(RegisterRequest registerRequest);

    LoginResponse userToLoginResponse(User user);
}
