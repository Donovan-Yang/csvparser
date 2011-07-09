package com.thoughtworks.csv.handler.typehandler;

import com.thoughtworks.csv.exception.CSVParseException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

public class DefaultTypeHandler implements TypeHandler {
    @Override
    public Object parse(Type fieldType, Map<Class, Annotation> mapper, String value) {
        return parse(fieldType, value);
    }

    private Object parse(Type fieldType, String value) {
        if (fieldType == String.class) {
            return value;
        } else if (fieldType == Integer.TYPE || fieldType == Integer.class) {
            return (value == null) ? 0 : Integer.parseInt(value);
        } else if (fieldType == Boolean.TYPE || fieldType == Boolean.class) {
            return Boolean.valueOf(value);
        } else if (fieldType == Double.TYPE || fieldType == Double.class) {
            return Double.parseDouble(value);
        }

        throw new CSVParseException(String.format("%s is not supported.", fieldType.getClass().getName()));
    }

}
