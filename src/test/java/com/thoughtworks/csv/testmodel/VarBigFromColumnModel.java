package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.annotation.CSVVarColumn;

import java.util.List;

public class VarBigFromColumnModel {
    @CSVVarColumn(from = 999)
    private List<Double> scores;
}
