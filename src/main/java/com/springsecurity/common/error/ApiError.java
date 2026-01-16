package com.springsecurity.common.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {

    private LocalDateTime timeStamp;
    private String error;
    private HttpStatus statusCode;


    public ApiError() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiError(String error, HttpStatus statusCode) {
        this();
        this.error = error;
        this.statusCode = statusCode;
    }
}
/*
package com.springsecurity.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {

    private int status;           // 401, 403, 404
    private String error;          // UNAUTHORIZED, FORBIDDEN
    private String errorCode;      // TOKEN_MISSING, BAD_CREDENTIALS
    private String message;        // Human readable
    private String path;           // /api/admin/dashboard

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public ApiError(HttpStatus status, String errorCode, String message, String path) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.errorCode = errorCode;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    // getters
}

 */