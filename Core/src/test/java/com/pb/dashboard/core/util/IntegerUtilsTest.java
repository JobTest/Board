package com.pb.dashboard.core.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by vlad
 * Date: 02.10.14
 */
public class IntegerUtilsTest {

    @Test
    public void testCheckStringOnNumberPositive() {
        String str = "123457";
        boolean b = IntegerUtil.checkInt(str);
        assertTrue(b);
    }

    @Test
    public void testCheckIntNull() throws Exception {
        boolean b = IntegerUtil.checkInt(null);
        assertFalse(b);
    }

    @Test
    public void testCheckIntObject() throws Exception {
        Object str = "11";
        boolean b = IntegerUtil.checkInt(str);
        assertTrue(b);
    }

    @Test
    public void testCheckStringOnNumberNegative() {
        String str = "a123457";
        boolean b = IntegerUtil.checkInt(str);
        assertFalse(b);
    }

    @Test
    public void testToIntNull() throws Exception {
        int bool = IntegerUtil.toInt(null);
        assertEquals(0, bool);
    }

    @Test
    public void testToIntFalse() throws Exception {
        int bool = IntegerUtil.toInt(false);
        assertEquals(0, bool);
    }

    @Test
    public void testName() throws Exception {
        int bool = IntegerUtil.toInt(true);
        assertEquals(1, bool);
    }
}