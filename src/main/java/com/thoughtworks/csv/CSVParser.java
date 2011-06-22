package com.thoughtworks.csv;

import au.com.bytecode.opencsv.CSVReader;
import com.thoughtworks.csv.annotation.CSVColumn;
import com.thoughtworks.csv.exception.CSVParseException;
import com.thoughtworks.csv.handler.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    public static final char SEPARATOR = ',';

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
        List<String[]> lines = reader.readAll();

        for (String[] columns : lines) {
            if (!isBlankLine(columns)) {
                results.add(parseLine(clazz, columns));
            }
        }

        return results;
    }

    private boolean isBlankLine(String[] columns) {
        return columns.length == 1 && columns[0].trim().length() == 0;
    }

    private <T> T parseLine(Class<T> clazz, String[] columns) {
        T instance = newInstance(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            CSVColumn annotation = field.getAnnotation(CSVColumn.class);
            if (annotation == null) {
                continue;
            }
            int index = annotation.index();
            String value = index >= columns.length ? null : columns[index].trim();
            setField(instance, field, value);
        }

        return instance;
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

    private <T> void setField(T instance, Field field, String value) {
        field.setAccessible(true);
        try {
            field.set(instance, parseValue(field, value));
        } catch (IllegalAccessException e) {
            throw new CSVParseException(e);
        }
    }

    private Object parseValue(Field field, String value) {
        CSVColumn annotation = field.getAnnotation(CSVColumn.class);
        try {
            Handler handler = (Handler) newInstance(annotation.handler());
            return handler.parse(field, value);
        } catch (Exception e) {
            throw new CSVParseException(e);
        }
    }
}