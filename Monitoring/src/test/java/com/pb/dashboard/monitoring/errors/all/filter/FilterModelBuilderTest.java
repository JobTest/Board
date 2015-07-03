package com.pb.dashboard.monitoring.errors.all.filter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FilterModelBuilderTest {

    @Test
    public void testDefaultDate() throws Exception {
        FilterModelI model = FilterModelBuilder.build();
        assertEquals(FilterModelBuilder.DEFAULT_TOP, model.getTopItem());
    }

    @Test
    public void testName() throws Exception {
        FilterModelI model = FilterModelBuilder.build();
        assertNotNull(model.getDate());
    }
}