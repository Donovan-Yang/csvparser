package com.thoughtworks.csv;

import au.com.bytecode.opencsv.CSVReader;
import com.thoughtworks.csv.annotation.ColumnType;
import com.thoughtworks.csv.exception.CSVParseException;
import com.thoughtworks.csv.handler.annotationhandler.AnnotationHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    public static final char SEPARATOR = ',';
    private boolean withHeader = false;

    public <T> List<T> parse(InputStream is, Class<T> clazz) {
        CSVReader reader = new CSVReader(new InputStreamReader(is), SEPARATOR);
        try {
            return parseFromCSV(clazz, reader);
        } catch (IOException e) {
            throw new CSVParseException(e);
        }
    }

    private <T> List<T> parseFromCSV(Class<T> clazz, CSVReader reader) throws IOException {
        List<T> results = new ArrayList<T>();
        List<String[]> lines = getAvailableLines(reader);

        for (String[] columns : lines) {
            if (!isBlankLine(columns)) {
                results.add(parseLine(clazz, columns));
            }
        }

        return results;
    }

    private List<String[]> getAvailableLines(CSVReader reader) throws IOException {
        List<String[]> lines = reader.readAll();
        return withHeader ? lines.subList(1, lines.size()) : lines;
    }

    private boolean isBlankLine(String[] columns) {
        return columns.length == 1 && columns[0].trim().length() == 0;
    }

    private <T> T parseLine(Class<T> clazz, String[] columns) {
        T instance = newInstance(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            setField(instance, field, parseValue(columns, field));
        }

        return instance;
    }

    private Object parseValue(String[] columns, Field field) {
        AnnotationHandler annotationHandler = getAnnotationHandler(field);
        return annotationHandler.parse(columns, field);
    }

    private AnnotationHandler getAnnotationHandler(Field field) {
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            ColumnType columnTypeAnnotation = annotation.annotationType().getAnnotation(ColumnType.class);
            if (columnTypeAnnotation == null) {
                continue;
            }
            return newInstance(columnTypeAnnotation.annotationHandler());
        }

        throw new CSVParseException(String.format("%s is not annotated with any of the column annotation.", field.getName()));
    }

    private <T> void setField(T instance, Field field, Object parsedValue) {
        try {
            field.setAccessible(true);
            field.set(instance, parsedValue);
        } catch (IllegalAccessException e) {
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

    public CSVParser withHeader(boolean withHeader) {
        this.withHeader = withHeader;
        return this;
    }
}