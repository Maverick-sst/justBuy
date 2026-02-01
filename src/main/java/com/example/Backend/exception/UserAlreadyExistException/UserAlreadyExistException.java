package com.example.Backend.exception.UserAlreadyExistException;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message){
        super(message);
    }
}
