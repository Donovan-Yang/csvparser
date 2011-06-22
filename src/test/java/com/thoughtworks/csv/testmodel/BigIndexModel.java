package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.annotation.CSVColumn;

public class BigIndexModel {
    @CSVColumn(index = 0)
    private int id;

    @CSVColumn(index = 1)
    private String name;

    @CSVColumn(index = 9999)
    private String bigIndexField;

    @CSVColumn(index = 9998)
    private int bigIndexInt;

    @CSVColumn(index = 9998)
    private boolean bigIndexBoolean;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBigIndexField() {
        return bigIndexField;
    }

    public int getBigIndexInt() {
        return bigIndexInt;
    }

    public boolean isBigIndexBoolean() {
        return bigIndexBoolean;
    }
}
