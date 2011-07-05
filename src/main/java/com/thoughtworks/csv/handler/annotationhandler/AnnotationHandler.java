package com.thoughtworks.csv.handler.annotationhandler;

import java.lang.reflect.Field;

public interface AnnotationHandler {
    Object parse(String[] columns, Field field);
}
