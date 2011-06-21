package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.CSVColumn;

public class BigIndexModel {
    @CSVColumn(0)
    private int id;

    @CSVColumn(1)
    private String name;

    @CSVColumn(9999)
    private String bigIndexField;

    @CSVColumn(9998)
    private int bigIndexInt;

    @CSVColumn(9998)
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
