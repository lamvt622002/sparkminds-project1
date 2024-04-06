package com.example.project1.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "emailVerification")
public class EmailVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String email;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expireTime;

    public EmailVerification(){

    }
    public EmailVerification(Long userId, String email, String token, LocalDateTime createdAt, LocalDateTime expireTime) {
        this.userId = userId;
        this.email = email;
        this.token = token;
        this.createdAt = createdAt;
        this.expireTime = expireTime;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "EmailVerification{" +
                "id=" + id +
                ", userId=" + userId +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", createdAt=" + createdAt +
                ", expireTime=" + expireTime +
                '}';
    }
}
