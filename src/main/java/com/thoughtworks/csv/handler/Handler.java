package com.thoughtworks.csv.handler;

import java.lang.reflect.Field;

public interface Handler {
    Object parse(Field field, String value);
}
