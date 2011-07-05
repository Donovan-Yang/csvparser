package com.thoughtworks.csv.handler.typehandler;

import java.lang.reflect.Field;

public interface TypeHandler {
    Object parse(Field field, String value);
}
