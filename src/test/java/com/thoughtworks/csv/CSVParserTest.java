package com.thoughtworks.csv;

import com.thoughtworks.csv.testmodel.BigIndexModel;
import com.thoughtworks.csv.testmodel.FieldTypeNotSupported;
import com.thoughtworks.csv.testmodel.Foo;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CSVParserTest {
    private CSVParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new CSVParser();
    }

    @Test
    public void should_parse_csv_file_to_pojo() {
        InputStream is = this.getClass().getResourceAsStream("/com/thoughtworks/csv/fixtures/foo.csv");

        List<Foo> foos = parser.parse(is, Foo.class);
        assertThat(foos.get(0).getId(), is(1));
        assertThat(foos.get(0).getName(), is("foo"));
        assertThat(foos.get(0).isSelected(), is(true));
        assertThat(foos.get(1).getId(), is(2));
        assertThat(foos.get(1).getName(), is("bar"));
        assertThat(foos.get(1).isSelected(), is(false));
        assertThat(foos.get(0).getPrice(), is(12.3));
    }

    @Test(expected = CSVParseException.class)
    public void should_throws_unsupported_field_type_exception_when_field_type_is_not_supported() {
        InputStream is = this.getClass().getResourceAsStream("/com/thoughtworks/csv/fixtures/foo.csv");
        parser.parse(is, FieldTypeNotSupported.class);
    }

    @Test
    public void should_parse_csv_with_quote() {
        InputStream is = this.getClass().getResourceAsStream("/com/thoughtworks/csv/fixtures/quote.csv");
        List<Foo> foos = parser.parse(is, Foo.class);
        assertThat(foos.get(0).getName(), is("quote, with comma"));
        assertThat(foos.get(1).getName(), is("normal blank"));
    }

    @Test
    public void should_parse_as_null_for_bigger_index_column() {
        InputStream is = this.getClass().getResourceAsStream("/com/thoughtworks/csv/fixtures/foo.csv");
        List<BigIndexModel> foos = parser.parse(is, BigIndexModel.class);

        assertThat(foos.get(0).getName(), is("foo"));
        assertThat(foos.get(0).getBigIndexField(), CoreMatchers.<String>nullValue());
    }
}
