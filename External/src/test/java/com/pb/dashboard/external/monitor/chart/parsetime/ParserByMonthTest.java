package com.pb.dashboard.external.monitor.chart.parsetime;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by vlad
 * Date: 10.11.14_13:33
 */
public class ParserByMonthTest {

    private static ParserTime parser;

    @BeforeClass
    public static void upTest() {
        parser = new ParserByMonth();
    }

    @AfterClass
    public static void downTest() {
        parser = null;
    }

    @Test
    public void testParsePositive() throws ParseException {
        int expYear = 2014;
        int expMonth = 7;

        String time = "2014-08";
        Calendar res = parser.parse(time);
        int resYear = res.get(Calendar.YEAR);
        int resMonth = res.get(Calendar.MONTH);
        assertEquals(expYear, resYear);
        assertEquals(expMonth, resMonth);
    }
}
