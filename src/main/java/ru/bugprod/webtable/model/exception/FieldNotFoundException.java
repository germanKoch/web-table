package ru.bugprod.webtable.model.exception;

public class FieldNotFoundException extends RuntimeException {
    public FieldNotFoundException() {
        super("Field not found");
    }
}
