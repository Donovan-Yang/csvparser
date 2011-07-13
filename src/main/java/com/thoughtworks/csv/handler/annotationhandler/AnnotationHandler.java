package com.thoughtworks.csv.handler.annotationhandler;

import com.thoughtworks.csv.exception.CSVParseException;
import com.thoughtworks.csv.handler.typehandler.TypeHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class AnnotationHandler {
    protected abstract Object parse(String[] columns, Field field, TypeHandler typeHandler);
    protected abstract void validate(String[] columns, Field field);
    protected abstract TypeHandler getHandler(Field field);

    public Object parse(String[] columns, Field field){
        validate(columns, field);
        return parse(columns, field, getHandler(field));
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
