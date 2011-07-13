package com.thoughtworks.csv.annotation;

import com.thoughtworks.csv.handler.annotationhandler.SingleColumnHandler;
import com.thoughtworks.csv.handler.typehandler.DefaultTypeHandler;
import com.thoughtworks.csv.handler.typehandler.TypeHandler;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@ColumnType(annotationHandler = SingleColumnHandler.class)
public @interface CSVColumn {
    int index();
    Class<? extends TypeHandler> typeHandler() default DefaultTypeHandler.class;
}
