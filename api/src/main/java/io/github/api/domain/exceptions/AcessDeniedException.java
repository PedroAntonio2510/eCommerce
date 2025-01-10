package io.github.api.domain.exceptions;

public class AcessDeniedException extends RuntimeException{

    public AcessDeniedException(String message){
        super(message);
    }
}
