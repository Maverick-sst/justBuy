package com.example.Backend.exception.GlobalExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.Backend.dto.ErrorResponse;
import com.example.Backend.exception.UserAlreadyExistException.UserAlreadyExistException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserExists(UserAlreadyExistException ex){
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    // to catch the dto @valid errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
    // to catch all the unexpected credential errors
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("Invalid email or password!"));
    }
    // catch all other unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex){
        // this will help us log the stack trace here
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                   .body(new ErrorResponse("Internal Server Down!"));
    }
    
}
