package ru.bugprod.webtable.model.exception;

public class OperationException extends RuntimeException {
    public OperationException() {
        super("Invalid operation");
    }
}
