package com.example.project1.payload.response;

import com.example.project1.enums.Role;
import com.example.project1.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JWTPayLoad {
    private Long userID;

    private String email;

    private String session;
}
