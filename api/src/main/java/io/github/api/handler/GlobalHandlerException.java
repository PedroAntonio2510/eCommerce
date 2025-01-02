package io.github.api.handler;

import com.fasterxml.jackson.core.JsonParseException;
import io.github.api.domain.exceptions.ObjectDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.net.URI;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethoArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getFieldErrors()
                .stream()
                .map(
                        fieldError -> Map.of(
                                "message", fieldError.getDefaultMessage(),
                                "field", fieldError.getField()
                        )).toList();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Validation Error"
        );

        problemDetail.setTitle("Validation Error");
        problemDetail.setType(URI.create("http://localhost:8080/errors/validation"));
        problemDetail.setProperty("errors", errors);

        return problemDetail;
    }

    @ExceptionHandler(ObjectDuplicateException.class)
    public ProblemDetail handleProductDuplicateException(ObjectDuplicateException ex) {
        String details = ex.getMessage();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, details);
        problemDetail.setTitle("Product alredy registered");
        problemDetail.setType(URI.create("http://localhost:8080/errors/duplicate"));
        return problemDetail;
    }

    @ExceptionHandler(NullPointerException.class)
    public ProblemDetail handleNullPointerException(NullPointerException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "The product id/quantity is null or doesn`t exists"
        );
        problemDetail.setTitle("Null Pointer Exception");
        problemDetail.setType(URI.create("http://localhost:8080/errors/null-pointer"));
        return problemDetail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleJsonParseException(HttpMessageNotReadableException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Invalid payment type"
        );
        problemDetail.setTitle("Json Parse Error");
        problemDetail.setType(URI.create("http://localhost:8080/errors/messagenotreadable"));
        problemDetail.setProperty("message", "There isn`t a valid payment method");
        return problemDetail;
    }

}
