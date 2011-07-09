package com.thoughtworks.csv.handler.annotationhandler;

import com.thoughtworks.csv.annotation.CSVVarColumn;
import com.thoughtworks.csv.exception.CSVParseException;
import com.thoughtworks.csv.handler.typehandler.TypeHandler;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VarColumnHandler extends AnnotationHandler {
    @Override
    public Object parse(String[] columns, Field field) {
        TypeHandler typeHandler = newInstance(field.getAnnotation(CSVVarColumn.class).typeHandler());
        Type fieldType = getFieldTypeForArray(field);
        String[] values = getValues(columns, field);
        List result = createArrayList(getFieldTypeForArray(field));
        for (String value : values) {
            result.add(typeHandler.parse(fieldType, getAnnotationMapper(field), value.trim()));
        }
        return result;
    }

    private String[] getValues(String[] columns, Field field) {
        CSVVarColumn annotation = field.getAnnotation(CSVVarColumn.class);
        int from = annotation.from();
        int length = columns.length;
        if (from > length - 1 || from < 0) {
            throw new CSVParseException("");
        }

        return Arrays.copyOfRange(columns, from, length);
    }

    private Type getFieldTypeForArray(Field field) {
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        return genericType.getActualTypeArguments()[0];
    }

    private <T> List<T> createArrayList(T type) {
        return new ArrayList<T>();
    }
}
