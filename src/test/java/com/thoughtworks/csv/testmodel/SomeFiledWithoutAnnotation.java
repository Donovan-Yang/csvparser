package com.thoughtworks.csv.testmodel;

public class SomeFiledWithoutAnnotation extends Foo{
    private String fieldWithoutAnnotation;

    public SomeFiledWithoutAnnotation(int id, String name, boolean selected, double price, String fieldWithoutAnnotation) {
        super(id, name, selected, price);
        this.fieldWithoutAnnotation= fieldWithoutAnnotation;
    }

    public String getFieldWithoutAnnotation() {
        return fieldWithoutAnnotation;
    }
}
