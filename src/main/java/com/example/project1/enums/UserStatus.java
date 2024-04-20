package com.example.project1.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserStatus {
    ACTIVE(1),
    INACTIVE(0),

    LOCK(-1),
    RESET_PASSWORD(2);

    private final int value;

    public static String fromValue(int value) {
        for (UserStatus status : UserStatus.values()) {
            if (status.getValue() == value) {
                return status.name();
            }
        }
        throw new IllegalArgumentException("Invalid UserStatus value: " + value);
    }
}
