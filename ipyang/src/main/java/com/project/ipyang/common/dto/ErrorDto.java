package com.project.ipyang.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorDto {
    private final String message;
    private final int code;


    public static ErrorDto of(Throwable throwable, HttpStatus status) {
        return new ErrorDto(throwable, status);
    }
    private ErrorDto(Throwable throwable, HttpStatus status) {
        this(throwable.getMessage(),status);
    }
    private ErrorDto(String errorMessage, HttpStatus status) {
        this.message = errorMessage;
        this.code = status.value();
    }
    public static ErrorDto of(String errorMessage, HttpStatus status) {
        return new ErrorDto(errorMessage,status);
    }
    private ErrorDto(String errorMessage, int errorCode) {
        this.message = errorMessage;
        this.code = errorCode;
    }
    public static ErrorDto of(String errorMessage, int errorCode) {
        return new ErrorDto(errorMessage, errorCode);
    }
}
