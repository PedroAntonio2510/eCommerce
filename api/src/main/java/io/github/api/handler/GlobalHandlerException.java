package io.github.api.handler;

import io.github.api.domain.exceptions.AcessDeniedException;
import io.github.api.domain.exceptions.ObjectDuplicateException;
import io.github.api.domain.exceptions.UserNotEnabledException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethoArgumentNotValidException(MethodArgumentNotValidException ex,
                                                              HttpServletRequest request) {
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
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        return problemDetail;
    }

    @ExceptionHandler(ObjectDuplicateException.class)
    public ProblemDetail handleProductDuplicateException(ObjectDuplicateException ex,
                                                         HttpServletRequest request) {
        String details = ex.getMessage();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, details);
        problemDetail.setType(URI.create("http://localhost:8080/errors/duplicate"));
        problemDetail.setTitle("Duplicate Object");
        problemDetail.setStatus(409);
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        return problemDetail;
    }

    @ExceptionHandler(NullPointerException.class)
    public ProblemDetail handleNullPointerException(HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "The product id/quantity is null or doesn`t exists"
        );
        problemDetail.setTitle("Null Pointer Exception");
        problemDetail.setType(URI.create("http://localhost:8080/errors/null-pointer"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        return problemDetail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleJsonParseException(HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Invalid payment type"
        );
        problemDetail.setTitle("Json Parse Error");
        problemDetail.setType(URI.create("http://localhost:8080/errors/messagenotreadable"));
        problemDetail.setProperty("message", "There isn`t a valid payment method");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        return problemDetail;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ProblemDetail handleUsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
        String details = ex.getMessage();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                details
        );
        problemDetail.setTitle("Username Not Found");
        problemDetail.setType(URI.create("http://localhost:8080/errors/usernameNotFound"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("message", "The login or password are invalid ");

        return problemDetail;
    }
    
    @ExceptionHandler(UserNotEnabledException.class)
    public ProblemDetail handleUserNotEnableException(UserNotEnabledException ex, HttpServletRequest request) {
        String details = ex.getMessage();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN, 
                "User not verified"
        );
        problemDetail.setTitle("User not verified");
        problemDetail.setType(URI.create("http://localhost:8080/errors/userEnable"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("message", ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(AcessDeniedException.class)
    public ProblemDetail handleAcessDeniedException(HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                "Acess denied"
        );
        problemDetail.setTitle("Acess Denied");
        problemDetail.setType(URI.create("http://localhost:8080/errors/acessDenied"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("message", "You don`t have acess to this resource");
        return problemDetail;
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntimeException(HttpServletRequest request) {
        ProblemDetail problemDetail= ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpect error ocorreded. Please try again later"
        );
        problemDetail.setTitle("Server error");
        problemDetail.setType(URI.create("http://localhost:8080/errors/runtime"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("message", "An unexpect error ocorreded. Please try again later");
        return problemDetail;
    }

}
