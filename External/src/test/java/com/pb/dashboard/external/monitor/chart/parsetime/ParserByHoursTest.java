package com.pb.dashboard.external.monitor.chart.parsetime;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by vlad
 * Date: 10.11.14_12:28
 */
public class ParserByHoursTest {

    private static ParserTime parser;

    @BeforeClass
    public static void upTest() {
        parser = new ParserByHours();
    }

    @AfterClass
    public static void downTest() {
        parser = null;
    }

    @Test
    public void testParsePositive() throws ParseException {
        int expHour = 18;
        int expMinute = 22;

        String time = "18:22";
        Calendar res = parser.parse(time);
        int resHour = res.get(Calendar.HOUR_OF_DAY);
        int resMinute = res.get(Calendar.MINUTE);
        assertEquals(expHour, resHour);
        assertEquals(expMinute, resMinute);
    }
}
