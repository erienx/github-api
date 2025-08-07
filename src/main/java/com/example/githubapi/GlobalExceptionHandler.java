package com.example.githubapi;

import com.example.githubapi.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleUserNotFoundException(UserNotFoundException e) {
        Map<String,Object> errResponse = new HashMap<>();
        errResponse.put("status", HttpStatus.NOT_FOUND.value());
        errResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
    }
}
