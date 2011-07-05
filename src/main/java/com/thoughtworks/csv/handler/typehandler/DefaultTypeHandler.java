package com.thoughtworks.csv.handler.typehandler;

import com.thoughtworks.csv.exception.CSVParseException;

import java.lang.reflect.Field;

public class DefaultTypeHandler implements TypeHandler {
    @Override
    public Object parse(Field field, String value) {
        Class<?> declaringClass = field.getType();

        if (declaringClass == String.class) {
            return value;
        } else if (declaringClass == Integer.TYPE) {
            return (value == null) ? 0 : Integer.parseInt(value);
        } else if (declaringClass == Boolean.TYPE) {
            return Boolean.valueOf(value);
        } else if (declaringClass == Double.TYPE) {
            return Double.parseDouble(value);
        }
        throw new CSVParseException(String.format("%s is not supported.", declaringClass.getName()));
    }

}
