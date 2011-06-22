package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.annotation.CSVColumn;

public class FieldTypeNotSupported {
    @CSVColumn(0)
    private Object fieldType;
}
