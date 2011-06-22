package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.CSVColumn;

public class HeroModel {
    @CSVColumn(0)
    private String page;

    @CSVColumn(1)
    private String space;

    @CSVColumn(3)
    private String overlayDesc;

    @CSVColumn(4)
    private String overlayLinkText;

    @CSVColumn(5)
    private String overlayLinkUrl;

    @CSVColumn(2)
    private String imageUrl;

    @CSVColumn(6)
    private String overlayTitle;
}
