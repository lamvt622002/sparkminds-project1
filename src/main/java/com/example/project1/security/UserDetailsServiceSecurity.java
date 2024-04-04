package com.example.project1.security;

import com.example.project1.entities.User;
import com.example.project1.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Service
public class UserDetailsServiceSecurity implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceSecurity(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Can not find user with email: " + email));

        UserDetailsSecurity userDetailsSecurity = new UserDetailsSecurity(user);

        return userDetailsSecurity;
    }
}
