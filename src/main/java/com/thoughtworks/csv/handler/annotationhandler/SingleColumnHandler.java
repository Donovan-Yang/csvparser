package com.thoughtworks.csv.handler.annotationhandler;

import com.thoughtworks.csv.annotation.CSVColumn;
import com.thoughtworks.csv.exception.CSVParseException;
import com.thoughtworks.csv.handler.typehandler.TypeHandler;
import java.lang.reflect.Field;

public class SingleColumnHandler extends AnnotationHandler {
    @Override
    public Object parse(String[] columns, Field field) {
        TypeHandler typeHandler = getTypeHandler(field);
        return typeHandler.parse(field.getType(), getAnnotationMapper(field), getValue(columns, field));
    }

    private String getValue(String[] columns, Field field) {
        int length = columns.length;
        CSVColumn annotation = field.getAnnotation(CSVColumn.class);
        int index = annotation.index();

        if (index > length - 1 || length < 0) {
            throw new CSVParseException(String.format("index (%s) of %s is invalid.", index, field.getName()));
        }

        return columns[index].trim();
    }
}
