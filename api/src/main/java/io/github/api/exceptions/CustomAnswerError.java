package io.github.api.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public record CustomAnswerError(
        String message,
        int status,
        List<CustomError> errorList
) {
    public static CustomAnswerError defaultAnswer(String message) {
        return new CustomAnswerError(message, HttpStatus.BAD_REQUEST.value(), List.of());
    }

    public static CustomAnswerError conflict(String message){
        return new CustomAnswerError(message, HttpStatus.CONFLICT.value(), List.of());
    }


}
