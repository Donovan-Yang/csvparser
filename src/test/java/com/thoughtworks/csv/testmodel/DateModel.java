package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.annotation.CSVColumn;
import com.thoughtworks.csv.annotation.DatePattern;
import com.thoughtworks.csv.handler.typehandler.DateHandler;

import java.util.Date;

public class DateModel {
    @CSVColumn(index = 0)
    private String weekday;

    @CSVColumn(index = 1, typeHandler = DateHandler.class)
    @DatePattern("yyyy-MM-dd")
    private Date startDate;

    @CSVColumn(index = 2, typeHandler = DateHandler.class)
    @DatePattern("yyyy/MM/dd")
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
