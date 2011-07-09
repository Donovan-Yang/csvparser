package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.annotation.CSVColumn;
import com.thoughtworks.csv.annotation.CSVVarColumn;

import java.util.List;

public class VarDoubleColumnModel {
    @CSVColumn(index = 0)
    private int id;

    @CSVVarColumn(from = 1)
    private List<Double> scores;

    public int getId() {
        return id;
    }

    public List<Double> getScores() {
        return scores;
    }
}
