package com.thoughtworks.csv;

import com.thoughtworks.csv.testmodel.DisorderdIndex;
import com.thoughtworks.csv.testmodel.Foo;
import com.thoughtworks.csv.testmodel.HasGapAmongAnnotationIndex;
import com.thoughtworks.csv.testmodel.SomeFiledWithoutAnnotation;
import org.junit.Test;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class CSVWriterTest {
    Foo foo = new Foo(1, "name", true, 13.9);
    StringWriter strWriter = new StringWriter();

    @Test
    public void should_write_object_to_csv_based_on_annotation_index() throws IllegalAccessException {
        CSVWriter csvWriter = new CSVWriter(strWriter);
        csvWriter.writeLine(new DisorderdIndex("1st", "3rd", "2nd"));

        assertEquals("1st,2nd,3rd\n", strWriter.toString());
    }

    @Test
    public void should_write_object_using_custom_config () throws IllegalAccessException {
        CSVWriter csvWriter = new CSVWriter(strWriter, '\'', '\"', "");
        csvWriter.writeLine(foo);

        assertEquals("\"1\"'\"name\"'\"true\"'\"13.9\"", strWriter.toString());
    }

    @Test
    public void should_write_batch_object_to_csv() throws IllegalAccessException {
        List<Object> foos = new ArrayList<Object>();
        foos.add(foo);
        foos.add(new Foo(2, "bar", false, 10.1));
        foos.add(new Foo(3, "pol", true, 20.2));

        CSVWriter csvWriter = new CSVWriter(strWriter);
        csvWriter.writeAll(foos);

        assertEquals("1,name,true,13.9\n" +
                "2,bar,false,10.1\n" +
                "3,pol,true,20.2\n", strWriter.toString());
    }

    @Test
    public void should_only_write_the_field_with_annotation() throws IllegalAccessException {
        CSVWriter csvWriter = new CSVWriter(strWriter);

        SomeFiledWithoutAnnotation obj = new SomeFiledWithoutAnnotation(2, "bar", false, 10.1, "anything");
        csvWriter.writeLine(obj);

        assertEquals("2,bar,false,10.1\n", strWriter.toString());
    }

    //@Test
    public void should_fill_the_gap_between_indexes_with_null() throws IllegalAccessException {
        CSVWriter csvWriter = new CSVWriter(strWriter);

        HasGapAmongAnnotationIndex obj = new HasGapAmongAnnotationIndex(2, "bar", false, 10.1, "anything");
        csvWriter.writeLine(obj);

        assertEquals("2,bar,false,10.1,,anything", strWriter.toString());
    }
}
