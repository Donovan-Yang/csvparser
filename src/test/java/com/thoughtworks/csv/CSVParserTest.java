package com.thoughtworks.csv;

import com.thoughtworks.csv.exception.CSVParseException;
import com.thoughtworks.csv.testmodel.*;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
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
        assertThat(foos.get(0).getBigIndexInt(), is(0));
        assertThat(foos.get(0).isBigIndexBoolean(), is(false));
    }

    @Test
    public void should_parse_heroes_ignoring_blank_line() {
        InputStream is = this.getClass().getResourceAsStream("/com/thoughtworks/csv/fixtures/heroes.csv");
        List<HeroModel> heroModels = parser.parse(is, HeroModel.class);
        assertThat(heroModels.size(), is(18));

    }

    @Test
    public void should_parse_date_time_by_given_annotated_format() {
        InputStream is = getInputStreamFromString("Monday, 2012-12-20, 2012/12/20\nThursday, 2010-10-10, 2012/12/20\n");
        List<DateModel> dateModels = parser.parse(is, DateModel.class);

        assertThat(dateModels.size(), is(2));
        assertThat(dateModels.get(0).getStartDate(), equalTo(createDate(2012, Calendar.DECEMBER, 20)));
        assertThat(dateModels.get(0).getEndDate(), equalTo(createDate(2012, Calendar.DECEMBER, 20)));
    }

    @Test(expected = CSVParseException.class)
    public void should_throw_exception_when_date_format_is_not_correct(){
        InputStream is = getInputStreamFromString("Monday, 2012.12.20, 2012/12/20");
        parser.parse(is, DateModel.class);
    }

    @Test
    public void should_parse_date_from_csv_file(){
        InputStream is = this.getClass().getResourceAsStream("/com/thoughtworks/csv/fixtures/date.csv");
        List<DateModel> dateModels = parser.parse(is, DateModel.class);
        assertThat(dateModels.size(), is(2));
        assertThat(dateModels.get(0).getStartDate(), equalTo(createDate(2012, Calendar.DECEMBER, 20)));
    }

    @Test
    public void should_parse_var_column() {
        InputStream is = getInputStreamFromString("1, black, white");
        List<VarColumnModel> varColumnModels = parser.parse(is, VarColumnModel.class);

        assertThat(varColumnModels.size(), is(1));

        VarColumnModel firstVarColumnModel = varColumnModels.get(0);

        assertThat(firstVarColumnModel.getTags().contains("black"), is(true));
        assertThat(firstVarColumnModel.getTags().contains("white"), is(true));
    }

    @Test
    @Ignore("will fix it later")
    public void should_parse_var_column_which_type_is_boolean(){
        InputStream is = getInputStreamFromString("1, true, false");
        List<VarBooleanColumnModel> varColumnModels = parser.parse(is, VarBooleanColumnModel.class);

        assertThat(varColumnModels.size(), is(1));

        VarBooleanColumnModel booleanColumnModel = varColumnModels.get(0);

        assertThat(booleanColumnModel.getId(), is(1));
        assertThat(booleanColumnModel.getBooleanList().get(0), is(true));
        assertThat(booleanColumnModel.getBooleanList().get(1), is(false));
    }
    private Date createDate(int year, int month, int dayOfMonth) {
        return new GregorianCalendar(year, month, dayOfMonth).getTime();
    }

    private InputStream getInputStreamFromString(String input) {
        InputStream is;
        try {
            is = new ByteArrayInputStream(input.getBytes("UTF-8"));
        } catch (Exception e) {
            return null;
        }
        return is;
    }
}
