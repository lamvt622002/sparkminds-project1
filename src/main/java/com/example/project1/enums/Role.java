package com.example.project1.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    ADMIN(1),
    USER(0),
    TEST(99);

    private final int value;

}
