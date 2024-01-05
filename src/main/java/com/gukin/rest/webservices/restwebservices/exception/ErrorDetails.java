package com.gukin.rest.webservices.restwebservices.exception;

import java.time.LocalDateTime;

public class ErrorDetails {
    // timestamp
    // message
    // details
    // 위 세 가지가 이 enterprise app 의 커스텀 예외 구조이다

    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ErrorDetails(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
