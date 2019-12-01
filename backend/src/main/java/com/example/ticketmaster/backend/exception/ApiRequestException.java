package com.example.ticketmaster.backend.exception;

public class ApiRequestException extends RuntimeException {

    public static final String NO_RECORDS_FOUND = "No records found for your request";
    public static final String WRONG = "Something went wrong";
    public static final String VALID = "Data input is not valid";

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
