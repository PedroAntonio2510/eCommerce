package io.github.api.domain.exceptions;

public class UserNotEnabledException extends RuntimeException{
    public UserNotEnabledException(String message){
        super(message);
    }
}
