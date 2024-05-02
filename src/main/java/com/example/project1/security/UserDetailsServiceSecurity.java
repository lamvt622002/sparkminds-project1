package com.example.project1.security;

import com.example.project1.constants.Constants;
import com.example.project1.entities.User;
import com.example.project1.exception.DataNotFoundException;
import com.example.project1.services.impl.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceSecurity implements UserDetailsService {
    private final UserServiceImpl userService;

    public UserDetailsServiceSecurity(UserServiceImpl userService) {
        this.userService = userService;
    }


    @Override
    public UserDetailsSecurity loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> getUser = userService.findUserByEmail(email);

        User user = getUser.orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND));

        UserDetailsSecurity userDetailsSecurity = new UserDetailsSecurity(user);

        return userDetailsSecurity;
    }
}
