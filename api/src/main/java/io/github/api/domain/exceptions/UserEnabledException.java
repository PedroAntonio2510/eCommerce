package io.github.api.domain.exceptions;

public class UserEnabledException extends RuntimeException{
    public UserEnabledException(String message){
        super(message);
    }
}
