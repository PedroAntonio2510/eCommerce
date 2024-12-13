package io.github.api.exceptions;

public record CustomError(
        String field,
        String error
) {
}
