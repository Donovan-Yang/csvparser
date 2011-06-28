package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.annotation.CSVColumn;

public class HasGapAmongAnnotationIndex extends Foo {
    @CSVColumn(index = 6)
    private String bigIndexProperty;

    public HasGapAmongAnnotationIndex(int id, String name, boolean selected, double price, String bigIndexProperty) {
        super(id, name, selected, price);
        this.bigIndexProperty= bigIndexProperty;
    }
}
