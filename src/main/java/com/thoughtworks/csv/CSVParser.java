package com.thoughtworks.csv;

import au.com.bytecode.opencsv.CSVReader;

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
        List<String[]> csvGroup = reader.readAll();
        for (String[] line : csvGroup) {
            results.add(parseLine(clazz, line));
        }
        return results;
    }

    private <T> T parseLine(Class<T> clazz, String[] columns) {
        T instance = newInstance(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            CSVColumn annotation = field.getAnnotation(CSVColumn.class);
            if (annotation == null) {
                continue;
            }
            int index = annotation.value();
            String value = index > columns.length ? null : columns[index].trim();
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
        Class<?> declaringClass = field.getType();

        if (declaringClass == String.class) {
            return value;
        } else if (declaringClass == Integer.TYPE) {
            return Integer.parseInt(value);
        } else if (declaringClass == Boolean.TYPE) {
            return Boolean.valueOf(value);
        } else if (declaringClass == Double.TYPE) {
            return Double.parseDouble(value);
        }

        throw new CSVParseException(String.format("%s is not supported.", declaringClass.getName()));
    }
}