package com.thoughtworks.csv.handler.annotationhandler;

import com.thoughtworks.csv.annotation.CSVColumn;
import com.thoughtworks.csv.exception.CSVParseException;
import com.thoughtworks.csv.handler.typehandler.TypeHandler;

import java.lang.reflect.Field;

public class SingleColumnHandler implements AnnotationHandler{
    @Override
    public Object parse(String[] columns, Field field) {
        CSVColumn annotation = field.getAnnotation(CSVColumn.class);
        if (annotation == null) {
            return null;
        }
        String value = annotation.index() >= columns.length ? null : columns[annotation.index()].trim();
        return parseValue(field, value);
    }

    private Object parseValue(Field field, String value) {
        CSVColumn annotation = field.getAnnotation(CSVColumn.class);
        try {
            TypeHandler handler = newInstance(annotation.typeHandler());

            return handler.parse(field, value);
        } catch (Exception e) {
            throw new CSVParseException(e);
        }
    }

    private <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new CSVParseException(e);
        } catch (IllegalAccessException e) {
            throw new CSVParseException(e);
        }
    }
}
