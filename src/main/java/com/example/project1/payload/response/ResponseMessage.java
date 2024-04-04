package com.example.project1.payload.response;

import com.example.project1.repository.ResponseRepository;

public class ResponseMessage <T> implements ResponseRepository {
    private boolean success;
    private int statusCode;
    private T data;

    public ResponseMessage(boolean success, int statusCode, T data) {
        this.success = success;
        this.statusCode = statusCode;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
