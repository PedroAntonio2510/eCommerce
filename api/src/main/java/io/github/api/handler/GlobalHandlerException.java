package io.github.api.handler;

import com.fasterxml.jackson.core.JsonParseException;
import io.github.api.exceptions.CustomAnswerError;
import io.github.api.exceptions.CustomError;
import io.github.api.exceptions.ProductDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public CustomAnswerError handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrorList = e.getFieldErrors();
        List<CustomError> errorList = fieldErrorList
                .stream()
                .map(fe -> new CustomError(
                        fe.getField(),
                        fe.getDefaultMessage()
                )).collect(Collectors.toList());
        return new CustomAnswerError(
                "Validation error",
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                errorList);
    }

    @ExceptionHandler(ProductDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CustomAnswerError handleProductDuplicateException(ProductDuplicateException e){
        return CustomAnswerError.conflict(e.getMessage());
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomAnswerError handleJsonParseException(JsonParseException e) {
        return CustomAnswerError.defaultAnswer(e.getMessage());
    }
}
