package com.pb.dashboard.external.monitor.chart.parsetime;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by vlad
 * Date: 10.11.14_13:45
 */
public class ParserByDayTest {

    private static ParserTime parser;

    @BeforeClass
    public static void upTest() {
        parser = new ParserByDay();
    }

    @AfterClass
    public static void downTest() {
        parser = null;
    }

    @Test
    public void testParsePositive() throws ParseException {
        int expYear = 2014;
        int expMonth = 11;
        int expDay = 4;

        String time = "2014-12-04";
        Calendar res = parser.parse(time);
        int resYear = res.get(Calendar.YEAR);
        int resMonth = res.get(Calendar.MONTH);
        int resDay = res.get(Calendar.DAY_OF_MONTH);
        assertEquals(expYear, resYear);
        assertEquals(expMonth, resMonth);
        assertEquals(expDay, resDay);
    }
}