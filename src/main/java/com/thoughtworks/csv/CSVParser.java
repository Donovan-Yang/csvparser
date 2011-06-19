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

    public <T> List<T> parse(InputStream is, Class<T> clazz) throws IOException, InstantiationException, IllegalAccessException {
        LineReader lineReader = new LineReader(new InputStreamReader(is));

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

    private <T> T parseLine(String line, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T instance = clazz.newInstance();
        String[] columns = line.split(SEPARATOR);
        for (Field field : clazz.getDeclaredFields()) {
            CSVColumn annotation = field.getAnnotation(CSVColumn.class);
            if (annotation == null) continue;
            setField(instance, field, columns[annotation.value()].trim());
        }

        return instance;
    }

    private <T> void setField(T instance, Field field, String value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(instance, parseValue(field, value));
    }

    private Object parseValue(Field field, String value) throws UnSupportedFieldTypeException {
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

        throw new UnSupportedFieldTypeException(declaringClass.getName());
    }
}