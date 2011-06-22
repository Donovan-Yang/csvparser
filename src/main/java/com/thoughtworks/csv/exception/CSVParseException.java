package com.thoughtworks.csv.exception;

public class CSVParseException extends RuntimeException {
    public CSVParseException() {
        super();
    }

    public CSVParseException(String s) {
        super(s);
    }

    public CSVParseException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CSVParseException(Throwable throwable) {
        super(throwable);
    }
}
