package ru.bugprod.webtable.model.exception;

public class ExportException extends RuntimeException {
    public ExportException(Throwable e) {
        super("Error while exporting occured.", e);
    }
}
