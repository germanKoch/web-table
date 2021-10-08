package ru.bugprod.webtable.model.exception;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}
