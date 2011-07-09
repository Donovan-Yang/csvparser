package com.thoughtworks.csv.handler.typehandler;

import com.thoughtworks.csv.annotation.DatePattern;
import com.thoughtworks.csv.exception.CSVParseException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Map;

public class DateHandler extends DefaultTypeHandler {
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public Object parse(Type fieldType, Map<Class, Annotation> mapper, String value) {
        DatePattern datePattern = (DatePattern) mapper.get(DatePattern.class);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(getDatePattern(datePattern));
            return dateFormat.parse(value);
        } catch (Exception e) {
            throw new CSVParseException(e);
        }
    }

    private String getDatePattern(DatePattern datePattern) {
        return datePattern == null ? DEFAULT_DATE_PATTERN : datePattern.value();
    }

}
