package com.thoughtworks.csv.annotation;

import com.thoughtworks.csv.handler.annotationhandler.VarColumnHandler;
import com.thoughtworks.csv.handler.typehandler.DefaultTypeHandler;
import com.thoughtworks.csv.handler.typehandler.TypeHandler;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Column(annotationHandler = VarColumnHandler.class)
public @interface CSVVarColumn {
    public int from();
    Class<? extends TypeHandler> typeHandler() default DefaultTypeHandler.class;
}
