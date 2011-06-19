package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.CSVColumn;
import com.thoughtworks.csv.UnSupportedFieldTypeException;

public class FieldTypeNotSupported {
    @CSVColumn(0)
    private UnSupportedFieldTypeException fieldType;
}
