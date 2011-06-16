package com.thoughtworks.csv;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.LineReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class CSVParser {
    public static final char SEPARATOR = ',';

    public <T> List<T> parse(InputStream is, Class<T> clazz) throws IOException, InstantiationException, IllegalAccessException {
        Reader reader = new InputStreamReader(is);
        LineReader lineReader = new LineReader(reader);
        Map<Integer, Field> fieldsMap = createFieldsMap(clazz);

        List<T> results = Lists.newArrayList();
        for (String line = lineReader.readLine(); line != null; line = lineReader.readLine()) {
            line = line.trim();
            if (line.length() == 0) {
                continue;
            }

            results.add(parseLine(line, clazz, fieldsMap));
        }

        return results;
    }

    private <T> T parseLine(String line, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        Map<Integer, Field> fieldsMap = createFieldsMap(clazz);

        return parseLine(line, clazz, fieldsMap);
    }

    private <T> T parseLine(String line, Class<T> clazz, Map<Integer, Field> fieldsMap) throws InstantiationException, IllegalAccessException {
        T instance = clazz.newInstance();
        Iterable<String> fields = Splitter.on(SEPARATOR).split(line);
        int i = 0;
        for (String field : fields) {
            i++;
            field = field.trim();
            if (field.length() == 0) {
                continue;
            }

            Field clazzField = fieldsMap.get(i);
            if (clazzField == null) {
                continue;
            }

            setField(instance, field, clazzField);
        }

        return instance;
    }


    private <T> void setField(T instance, String field, Field clazzField) throws IllegalAccessException {
        clazzField.setAccessible(true);
        Class<?> declaringClass = clazzField.getType();
        if (declaringClass == String.class) {
            clazzField.set(instance, field);
        } else if (declaringClass == Integer.TYPE) {
            clazzField.setInt(instance, Integer.parseInt(field));
        }
    }

    private <T> Map<Integer, Field> createFieldsMap(Class<T> clazz) {
        Map<Integer, Field> fieldsMap = Maps.newHashMap();
        Field[] clazzFields = clazz.getDeclaredFields();
        for (Field clazzField : clazzFields) {
            CSVColumn annotation = clazzField.getAnnotation(CSVColumn.class);
            fieldsMap.put(annotation.value(), clazzField);
        }

        return fieldsMap;
    }
}