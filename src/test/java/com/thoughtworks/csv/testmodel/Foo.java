package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.annotation.CSVColumn;

public class Foo {
    @CSVColumn(index = 0)
    private int id;

    @CSVColumn(index = 1)
    private String name;

    @CSVColumn(index = 2)
    private boolean isSelected;

    @CSVColumn(index = 3)
    private double price;

    public Foo(){}

    public Foo(int id, String name, boolean selected, double price) {
        this.id = id;
        this.name = name;
        isSelected = selected;
        this.price = price;
    }

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
