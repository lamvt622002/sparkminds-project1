package com.example.project1.utitilies;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordGenerator {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SPECIAL_CHARACTER = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final String DIGITS = "0123456789";

    private static final String ALL_CHARACTERS = LOWERCASE + UPPERCASE + SPECIAL_CHARACTER + DIGITS;
    private static final SecureRandom random = new SecureRandom();


    public String generatePassword(){
        StringBuilder password = new StringBuilder();
        password.append(getRandomChar(LOWERCASE));
        password.append(getRandomChar(UPPERCASE));
        password.append(getRandomChar(SPECIAL_CHARACTER));
        password.append(getRandomChar(DIGITS));
        for (int i = 4; i < 6; i++) {
            password.append(getRandomChar(ALL_CHARACTERS));
        }
        return password.toString();
    }

    private static char getRandomChar(String character){
        int randomIndex = random.nextInt(character.length());
        return character.charAt(randomIndex);
    }
}
