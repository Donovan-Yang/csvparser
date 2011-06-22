package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.annotation.CSVColumn;

public class Foo {
    @CSVColumn(0)
    private int id;

    @CSVColumn(1)
    private String name;

    @CSVColumn(2)
    private boolean isSelected;

    @CSVColumn(3)
    private double price;

    public double getPrice() {
        return price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
