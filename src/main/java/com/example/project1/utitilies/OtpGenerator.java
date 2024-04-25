package com.example.project1.utitilies;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpGenerator {
    public String getRandomOtp(){
        Random random = new Random();
        int number = random.nextInt(999999);
        return String.format("%06d", number);
    }
}
