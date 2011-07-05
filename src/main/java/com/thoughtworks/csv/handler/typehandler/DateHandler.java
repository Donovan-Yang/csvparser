package com.thoughtworks.csv.handler.typehandler;

import com.thoughtworks.csv.annotation.DatePattern;
import com.thoughtworks.csv.exception.CSVParseException;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHandler extends DefaultTypeHandler {
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    public Date parse(Field field, String value) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(getDatePattern(field));
            return dateFormat.parse(value);
        } catch (Exception e) {
            throw new CSVParseException(e);
        }
    }

    private String getDatePattern(Field field) {
        DatePattern datePattern = field.getAnnotation(DatePattern.class);
        return datePattern == null ? DEFAULT_DATE_PATTERN : datePattern.value();
    }

}
