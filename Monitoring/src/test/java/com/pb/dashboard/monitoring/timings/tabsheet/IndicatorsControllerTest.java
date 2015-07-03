package com.pb.dashboard.monitoring.timings.tabsheet;

import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceLimit;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceLimitI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IndicatorsControllerTest {

    private IndicatorsModelI model;
    private IndicatorsControllerI controller;

    @Before
    public void setUp() throws Exception {
        model = new IndicatorsModel();
        controller = new IndicatorsController(model);
    }

    @Test
    public void testSetLimit() throws Exception {
        InterfaceLimitI limit = new InterfaceLimit();
        controller.setLimit(limit);
        assertEquals(model.getLimit(), limit);
    }
    @Test
    public void testSetLimitNull() throws Exception {
        InterfaceLimitI limit = new InterfaceLimit();
        model.setLimit(limit);
        controller.setLimit(null);
        assertEquals(limit, model.getLimit());
    }
}