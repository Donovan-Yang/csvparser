package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.annotation.CSVColumn;
import com.thoughtworks.csv.annotation.CSVVarColumn;

import java.util.List;

public class VarIntColumnModel {
    @CSVColumn(index = 0)
    private int id;

    @CSVVarColumn(from = 1)
    private List<Integer> scores;

    public int getId() {
        return id;
    }

    public List<Integer> getScores() {
        return scores;
    }
}

