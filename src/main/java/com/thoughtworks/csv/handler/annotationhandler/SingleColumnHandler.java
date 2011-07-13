package com.thoughtworks.csv.handler.annotationhandler;

import com.thoughtworks.csv.annotation.CSVColumn;
import com.thoughtworks.csv.exception.CSVParseException;
import com.thoughtworks.csv.handler.typehandler.TypeHandler;
import java.lang.reflect.Field;

public class SingleColumnHandler extends AnnotationHandler {
    @Override
    protected Object parse(String[] columns, Field field, TypeHandler typeHandler) {
        return typeHandler.parse(field.getType(), getAnnotationMapper(field), getValue(columns, field));
    }

    @Override
    protected void validate(String[] columns, Field field) {
        CSVColumn annotation = field.getAnnotation(CSVColumn.class);
        int length = columns.length;
        int index = annotation.index();

        if (index > length - 1 || length < 0) {
            throw new CSVParseException(String.format("index (%s) of %s is invalid.", index, field.getName()));
        }
    }

    @Override
    protected TypeHandler getHandler(Field field) {
        return newInstance(field.getAnnotation(CSVColumn.class).typeHandler());
    }

    private String getValue(String[] columns, Field field) {
        CSVColumn annotation = field.getAnnotation(CSVColumn.class);
        return columns[annotation.index()].trim();
    }
}
