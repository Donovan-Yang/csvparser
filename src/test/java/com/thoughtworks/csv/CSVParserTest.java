package com.thoughtworks.csv;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CSVParserTest {
    @Test
    public void should_parse_csv_file_to_pojo() throws IOException, IllegalAccessException, InstantiationException {
        InputStream is = this.getClass().getResourceAsStream("/com/thoughtworks/csv/foo.csv");

        CSVParser parser = new CSVParser();

        List<Foo> foos = parser.parse(is, Foo.class);
        assertThat(foos.get(0).getId(), is(1));
        assertThat(foos.get(0).getName(), is("foo"));
        assertThat(foos.get(1).getId(), is(2));
        assertThat(foos.get(1).getName(), is("bar"));
    }
}
