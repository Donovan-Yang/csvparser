package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.annotation.CSVColumn;
import com.thoughtworks.csv.annotation.DateType;

import java.util.Date;

public class DateModel {
    @CSVColumn(0)
    private String weekday;

    @CSVColumn(1)
    @DateType(format = "yyyy-MM-dd")
    private Date date;

    public Date getDate() {
        return date;
    }
}
