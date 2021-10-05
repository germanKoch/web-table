package ru.bugprod.webtable.repository.service.filter.model.exception;

public class IllegalConditionException extends RuntimeException {
    public IllegalConditionException(String message) {
        super(message);
    }

    public IllegalConditionException(String message, Throwable cause) {
        super(message, cause);
    }
}
