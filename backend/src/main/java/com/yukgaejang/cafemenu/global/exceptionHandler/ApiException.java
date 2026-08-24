package com.yukgaejang.cafemenu.global.exceptionHandler;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
