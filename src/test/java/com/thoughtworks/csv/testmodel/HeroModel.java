package com.thoughtworks.csv.testmodel;

import com.thoughtworks.csv.annotation.CSVColumn;

public class HeroModel {
    @CSVColumn(index = 0)
    private String page;

    @CSVColumn(index = 1)
    private String space;

    @CSVColumn(index = 3)
    private String overlayDesc;

    @CSVColumn(index = 4)
    private String overlayLinkText;

    @CSVColumn(index = 5)
    private String overlayLinkUrl;

    @CSVColumn(index = 2)
    private String imageUrl;

    @CSVColumn(index = 6)
    private String overlayTitle;
}
