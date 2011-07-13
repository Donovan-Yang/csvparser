package com.thoughtworks.csv.handler.annotationhandler;

import com.thoughtworks.csv.annotation.CSVVarColumn;
import com.thoughtworks.csv.exception.CSVParseException;
import com.thoughtworks.csv.handler.typehandler.TypeHandler;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VarColumnHandler extends AnnotationHandler {
    protected void validate(String[] columns, Field field) {
        CSVVarColumn annotation = field.getAnnotation(CSVVarColumn.class);
        int from = annotation.from();
        int length = columns.length;
        if (from > length - 1 || from < 0) {
            throw new CSVParseException("");
        }
    }

    protected Object parse(String[] columns, Field field, TypeHandler typeHandler) {
        List result = createArrayList(getType(field));

        for (String value : getValues(columns, field)) {
            result.add(typeHandler.parse(getType(field), getAnnotationMapper(field), value.trim()));
        }
        return result;
    }

    protected TypeHandler getHandler(Field field) {
        return newInstance(field.getAnnotation(CSVVarColumn.class).typeHandler());
    }

    private String[] getValues(String[] columns, Field field) {
        int from = field.getAnnotation(CSVVarColumn.class).from();
        return Arrays.copyOfRange(columns, from, columns.length);
    }

    private Type getType(Field field) {
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        return genericType.getActualTypeArguments()[0];
    }

    private <T> List<T> createArrayList(T type) {
        return new ArrayList<T>();
    }
}
