package y2017.m12.d05;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/12/5
 */
public class TestLocalDate {
    @Test
    public void testLocalDate() {
        String solrDate = "20171115165630";
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        System.out.println(LocalDateTime.parse(solrDate, format).getLong(ChronoField.MICRO_OF_SECOND));
    }

    @Test
    public void testJoda() {
        String solrDate = "20171115165630000";
        DateTime dateTime = DateTimeFormat.forPattern("yyyyMMddHHmmssSSS").parseDateTime(solrDate);
        System.out.println(dateTime);
    }
}
