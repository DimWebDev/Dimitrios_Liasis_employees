package com.dimitriosliasis.employees.backend.dto;

import java.time.Instant;

/**
 * Standard error payload for controller advice.
 */
public class ErrorDto {
    private final Instant timestamp;
    private final int status;
    private final String error;
    private final String message;

    public ErrorDto(Instant timestamp, int status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
