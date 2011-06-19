package com.thoughtworks.csv;

public class UnSupportedFieldTypeException extends RuntimeException {
    private String unsupportedType;

    public String getMessage() {
        return String.format("%s is not supported yet.", unsupportedType);
    }

    public UnSupportedFieldTypeException(String unsupportedType) {
        this.unsupportedType = unsupportedType;
    }
}
