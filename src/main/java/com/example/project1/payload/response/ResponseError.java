package com.example.project1.payload.response;

import com.example.project1.repository.ResponseRepository;

public class ResponseError <T> implements ResponseRepository {
    private boolean success;
    private int status;
    private T data;

    public ResponseError(boolean success, int status, T data) {
        this.success = success;
        this.status = status;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
