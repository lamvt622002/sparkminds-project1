package com.example.project1.utitilies;

public final class StringUtils {
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).{6,}$";

    public static String encloseInCurlyBraces(String input) {
        return String.format("{%s}", input);
    }
}
