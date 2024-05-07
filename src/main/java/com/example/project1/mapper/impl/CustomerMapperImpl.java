package com.example.project1.mapper.impl;

import com.example.project1.entities.Authorities;
import com.example.project1.entities.Customer;
import com.example.project1.entities.User;
import com.example.project1.enums.Role;
import com.example.project1.enums.UserStatus;
import com.example.project1.exception.DataNotFoundException;
import com.example.project1.mapper.CustomerMapper;
import com.example.project1.payload.request.RegisterRequest;
import com.example.project1.payload.response.LoginResponse;
import com.example.project1.services.AuthoritiesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomerMapperImpl implements CustomerMapper {
    private final ModelMapper mapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthoritiesService authoritiesService;
    @Override
    public Customer registerRequestToCustomer(RegisterRequest registerRequest) {
        Converter<RegisterRequest, Customer> registerConverter = context ->{
            String passwordEncode = passwordEncoder.encode(registerRequest.getPassword());

            Authorities defaultAuthority = authoritiesService.findAuthorityByAuthority(Role.USER.name())
                    .orElseThrow(() -> new DataNotFoundException("Invalid authority"));

            RegisterRequest source = context.getSource();
            Customer customer = Customer.builder().firstName(source.getFirstName())
                    .lastName(source.getLastName())
                    .birthDay(source.getBirthDay())
                    .phoneNumber(source.getPhoneNumber())
                    .email(source.getEmail())
                    .password(passwordEncode)
                    .failedLoginAttempts(0)
                    .role(defaultAuthority)
                    .status(UserStatus.INACTIVE.getValue())
                    .build();
            System.out.println(customer);
            return customer;
        };
        mapper.createTypeMap(RegisterRequest.class, Customer.class).setConverter(registerConverter);

        return mapper.map(registerRequest, Customer.class);
    }

    @Override
    public LoginResponse userToLoginResponse(User user) {
        Converter<User, LoginResponse> loginConverter = context -> {
            User source = context.getSource();
            if(source instanceof Customer customer){
                return LoginResponse.builder()
                        .firstName(customer.getFirstName())
                        .lastName(customer.getLastName())
                        .email(customer.getEmail())
                        .birthDay(customer.getBirthDay())
                        .phoneNumber(customer.getPhoneNumber())
                        .role(customer.getRole().getAuthority())
                        .status(UserStatus.fromValue(customer.getStatus()))
                        .build();
            }
            return null;
        };
        TypeMap<User, LoginResponse> typeMap = mapper.getTypeMap(User.class, LoginResponse.class);
        if (typeMap == null) {
            mapper.createTypeMap(User.class, LoginResponse.class).setConverter(loginConverter);
        }

        return mapper.map(user, LoginResponse.class);
    }
}
