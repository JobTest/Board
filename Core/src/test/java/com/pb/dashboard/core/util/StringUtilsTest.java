package com.pb.dashboard.core.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by vlad
 * Date: 22.10.14_9:05
 */
public class StringUtilsTest {

    @Test
    public void testIsEmptyOrNull() throws Exception {
        String str = "123";
        boolean emptyOrNull = StringUtil.isEmptyOrNull(str);
        assertFalse(emptyOrNull);
    }

    @Test
    public void testIsNull() throws Exception {
        String str = null;
        boolean emptyOrNull = StringUtil.isEmptyOrNull(str);
        assertTrue(emptyOrNull);
    }

    @Test
    public void testIsEmpty() throws Exception {
        String str = "";
        boolean emptyOrNull = StringUtil.isEmptyOrNull(str);
        assertTrue(emptyOrNull);
    }
}
