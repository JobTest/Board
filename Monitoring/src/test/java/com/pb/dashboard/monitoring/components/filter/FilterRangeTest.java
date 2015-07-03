package com.pb.dashboard.monitoring.components.filter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FilterRangeTest {

    @Test
    public void testPkeyToFilterByInt() throws Exception {
        FilterRange expected = FilterRange.HOUR;
        FilterRange actual = FilterRange.pkeyToFilterRange(expected.getPkey());
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPkeyToFilterByIntOutside() throws Exception {
        int filterId = 100;
        FilterRange.pkeyToFilterRange(filterId);
    }

    @Test
    public void testPkeyToFilterByStr() throws Exception {
        FilterRange expected = FilterRange.HOUR;
        String rangeId = String.valueOf(expected.getPkey());
        FilterRange actual = FilterRange.pkeyToFilterRange(rangeId);
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPkeyToFilterByStrOutside() throws Exception {
        String filterId = "100";
        FilterRange.pkeyToFilterRange(filterId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPkeyToFilterByStrNull() throws Exception {
        FilterRange.pkeyToFilterRange(null);
    }
}