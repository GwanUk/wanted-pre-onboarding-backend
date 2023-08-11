package com.example.wantedpreonboardingbackend.common.controller;

import com.example.wantedpreonboardingbackend.common.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unauthorizedException(UnauthorizedException exception) {
        return "권한이 없습니다.";
    }
}
