package com.example.project1.enums;

import lombok.Getter;

@Getter
public enum SessionStatus {
    ACTIVE_SESSION(1),
    INACTIVE_SESSION(0);
    private final int value;

    SessionStatus(int value) {
        this.value = value;
    }
}
