package com.thoughtworks.csv.handler.typehandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

public interface TypeHandler {
    Object parse(Type fieldType, Map<Class, Annotation> mapper, String value);
}
