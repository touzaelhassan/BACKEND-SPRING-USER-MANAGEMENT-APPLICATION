package com.application.exceptions;

public class UsernameExistException extends Exception{
    public UsernameExistException(String message) {
        super(message);
    }
}
