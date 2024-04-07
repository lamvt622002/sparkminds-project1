package com.example.project1.security;

import com.example.project1.entities.User;
import com.example.project1.exception.DataNotFoundExeption;
import com.example.project1.repository.UserRepository;
import com.example.project1.services.impl.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> getUser = userService.findUserByEmail(email);

        User user = getUser.orElseThrow(() -> new DataNotFoundExeption("User not found"));

        UserDetailsSecurity userDetailsSecurity = new UserDetailsSecurity(user);

        return userDetailsSecurity;
    }
}
