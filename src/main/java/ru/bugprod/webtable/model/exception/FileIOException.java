package ru.bugprod.webtable.model.exception;

public class FileIOException extends RuntimeException {
    public FileIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
