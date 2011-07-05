package com.thoughtworks.csv.handler.annotationhandler;

import com.thoughtworks.csv.annotation.CSVVarColumn;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;

public class VarColumnHandler implements AnnotationHandler {
    @Override
    public Object parse(String[] columns, Field field) {
        CSVVarColumn annotation = field.getAnnotation(CSVVarColumn.class);
        if (annotation == null) {
            return null;
        }
        return getVarColumnObject(columns, annotation);
    }

    private Object getVarColumnObject(String[] columns, CSVVarColumn annotation) {
        int from = annotation.from();

        String[] result = (String[]) Array.newInstance(String.class, columns.length - from);
        for (int index = from; index < columns.length; index++) {
            Array.set(result, index - from, columns[index].trim());
        }
        return Arrays.asList(result);
    }
}
