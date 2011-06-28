package com.thoughtworks.csv;

import com.thoughtworks.csv.annotation.CSVColumn;

import java.io.Writer;
import java.lang.reflect.Field;
import java.util.*;

public class CSVWriter {
    au.com.bytecode.opencsv.CSVWriter rawWriter;
    public static final char DEFAULT_SEPARATOR = ',';
    public static final char NO_QUOTE_CHARACTER = '\u0000';
    public static final String DEFAULT_LINE_END = "\n";

    public CSVWriter(Writer writer, char separator, char quotchar, String lineEnd) {
        rawWriter = new au.com.bytecode.opencsv.CSVWriter(writer, separator, quotchar, lineEnd);
    }

    public CSVWriter(Writer writer){
        this(writer, DEFAULT_SEPARATOR, NO_QUOTE_CHARACTER, DEFAULT_LINE_END);
    }

    public void writeLine(Object foo) throws IllegalAccessException {
        List<Field> fields = getAllAnnotatedFields(foo);
        sortByAnnotationIndex(fields);
        rawWriter.writeNext(ColumnsFromFields(fields,  foo));
    }

    private List<Field> getAllAnnotatedFields(Object foo) {
        List<Field> fields = new LinkedList<Field>();
        Class clazz = foo.getClass();
        while(clazz != null){
            for(Field field : clazz.getDeclaredFields()){
                if(field.getAnnotation(CSVColumn.class) != null){
                    fields.add(field);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private String[] ColumnsFromFields(List<Field> fields, Object foo) throws IllegalAccessException {
        String[] columns = new String[fields.size()];
        for(int i = 0;i < fields.size();i++){
            fields.get(i).setAccessible(true);
            columns[i] = fields.get(i).get(foo).toString();
        }
        return columns;
    }

    private void sortByAnnotationIndex(List<Field> fields) {
        Collections.sort(fields, new Comparator<Field>(){
            @Override
            public int compare(Field f1, Field f2) {
                return f1.getAnnotation(CSVColumn.class).index()
                        > f2.getAnnotation(CSVColumn.class).index() ? 1 : 0;
            }
        });
    }

    public void writeAll(List<Object> objs) throws IllegalAccessException {
        for(Object obj : objs){
            writeLine(obj);
        }
    }
}
