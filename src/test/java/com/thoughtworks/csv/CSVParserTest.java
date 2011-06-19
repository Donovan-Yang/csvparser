package com.thoughtworks.csv;

import com.thoughtworks.csv.testmodel.FieldTypeNotSupported;
import com.thoughtworks.csv.testmodel.Foo;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CSVParserTest {
    @Test
    public void should_parse_csv_file_to_pojo() throws IOException, IllegalAccessException, InstantiationException {
        InputStream is = this.getClass().getResourceAsStream("/com/thoughtworks/csv/fixtures/foo.csv");

        CSVParser parser = new CSVParser();

        List<Foo> foos = parser.parse(is, Foo.class);
        assertThat(foos.get(0).getId(), is(1));
        assertThat(foos.get(0).getName(), is("foo"));
        assertThat(foos.get(0).isSelected(), is(true));
        assertThat(foos.get(1).getId(), is(2));
        assertThat(foos.get(1).getName(), is("bar"));
        assertThat(foos.get(1).isSelected(), is(false));
        assertThat(foos.get(0).getPrice(), is(12.3));
    }

    @Test
    public void should_throws_unsupported_field_type_exception_when_field_type_is_not_supported() throws IOException, IllegalAccessException, InstantiationException {
        InputStream is = this.getClass().getResourceAsStream("/com/thoughtworks/csv/fixtures/foo.csv");
        CSVParser parser = new CSVParser();
        try{
            parser.parse(is, FieldTypeNotSupported.class);
        }catch (UnSupportedFieldTypeException e){
            assertThat(e, instanceOf(UnSupportedFieldTypeException.class));
            assertThat(e.getMessage(), equalTo("com.thoughtworks.csv.UnSupportedFieldTypeException is not supported yet."));
        }
    }
}
