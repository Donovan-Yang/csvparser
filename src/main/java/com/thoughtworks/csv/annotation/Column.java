package com.thoughtworks.csv.annotation;

import com.thoughtworks.csv.handler.annotationhandler.AnnotationHandler;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    Class<? extends AnnotationHandler> annotationHandler();
}
