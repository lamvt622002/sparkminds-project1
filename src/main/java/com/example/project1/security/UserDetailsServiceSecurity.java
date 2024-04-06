package com.example.project1.security;

import com.example.project1.entities.User;
import com.example.project1.repository.UserRepository;
import com.example.project1.services.impl.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UserDetailsServiceSecurity implements UserDetailsService {
    private final UserServiceImpl userService;

    public UserDetailsServiceSecurity(UserServiceImpl userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);

        UserDetailsSecurity userDetailsSecurity = new UserDetailsSecurity(user);

        return userDetailsSecurity;
    }
}
