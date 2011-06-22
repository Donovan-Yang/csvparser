package com.thoughtworks.csv.annotation;

import com.thoughtworks.csv.handler.DefaultHandler;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CSVColumn {
    int index();

    //TODO how to define the generic method that should return the class which has implemented
    //interface Handler.
    Class  handler() default DefaultHandler.class;
}
