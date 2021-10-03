package ru.bugprod.webtable.model.exception;

public class TableNotFoundException extends RuntimeException{
    public TableNotFoundException(String message) {
        super(message);
    }
}
