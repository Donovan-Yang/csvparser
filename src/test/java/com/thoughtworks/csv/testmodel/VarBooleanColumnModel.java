package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.annotation.CSVColumn;
import com.thoughtworks.csv.annotation.CSVVarColumn;

import java.util.List;

public class VarBooleanColumnModel {
    @CSVColumn(index = 0)
    private int id;

    @CSVVarColumn(from = 1)
    private List<Boolean> booleanList;

    public int getId() {
        return id;
    }

    public List<Boolean> getBooleanList() {
        return booleanList;
    }
}
