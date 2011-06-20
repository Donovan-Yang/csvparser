package com.thoughtworks.csv;

import com.google.common.collect.Lists;
import com.google.common.io.LineReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.List;

public class CSVParser {
    public static final String SEPARATOR = ",";

    public <T> List<T> parse(InputStream is, Class<T> clazz) {
        try {
            return parseLines(clazz, new LineReader(new InputStreamReader(is)));
        } catch (IOException e) {
            throw new CSVParseException(e);
        }
    }

    private <T> List<T> parseLines(Class<T> clazz, LineReader lineReader) throws IOException {
        List<T> results = Lists.newArrayList();
        for (String line = lineReader.readLine(); line != null; line = lineReader.readLine()) {
            line = line.trim();
            if (line.length() == 0) {
                continue;
            }
            results.add(parseLine(line, clazz));
        }
        return results;
    }

    private <T> T parseLine(String line, Class<T> clazz) {
        T instance = newInstance(clazz);

        String[] columns = line.split(SEPARATOR);
        for (Field field : clazz.getDeclaredFields()) {
            CSVColumn annotation = field.getAnnotation(CSVColumn.class);
            if (annotation == null) {
                continue;
            }
            setField(instance, field, columns[annotation.value()].trim());
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