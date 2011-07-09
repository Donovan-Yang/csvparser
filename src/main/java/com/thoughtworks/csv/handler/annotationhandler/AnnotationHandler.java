package com.thoughtworks.csv.handler.annotationhandler;

import com.thoughtworks.csv.annotation.CSVColumn;
import com.thoughtworks.csv.exception.CSVParseException;
import com.thoughtworks.csv.handler.typehandler.TypeHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class AnnotationHandler {
    public abstract Object parse(String[] columns, Field field);

    public TypeHandler getTypeHandler(Field field) {
        CSVColumn annotation = field.getAnnotation(CSVColumn.class);
        if (annotation != null) {
            return newInstance(annotation.typeHandler());
        }
        throw new CSVParseException("");
    }

    public Map<Class, Annotation> getAnnotationMapper(Field field) {
        HashMap<Class, Annotation> mapper = new HashMap<Class, Annotation>();
        for (Annotation annotation : field.getAnnotations()) {
            mapper.put(annotation.annotationType(), annotation);
        }
        return mapper;
    }

    protected <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new CSVParseException(e);
        } catch (IllegalAccessException e) {
            throw new CSVParseException(e);
        }
    }
}
