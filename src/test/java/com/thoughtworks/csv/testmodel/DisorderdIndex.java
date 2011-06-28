package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.annotation.CSVColumn;

public class DisorderdIndex {
    @CSVColumn(index = 0)
    private String first;

    @CSVColumn(index = 2)
    private String third;

    @CSVColumn(index = 1)
    private String second;

    public DisorderdIndex(String first, String third, String second) {
        this.first = first;
        this.third = third;
        this.second = second;
    }
}
