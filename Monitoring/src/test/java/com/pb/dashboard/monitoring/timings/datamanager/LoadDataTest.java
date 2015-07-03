package com.pb.dashboard.monitoring.timings.datamanager;

import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceLimitI;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.service.ServiceFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

import static org.junit.Assert.*;

public class LoadDataTest {

    private LoadData loadData;

    @BeforeClass
    public static void up() throws Exception {
        new ClassPathXmlApplicationContext("/monitoring-context.xml");
    }

    @Before
    public void setUp() throws Exception {
        loadData = new LoadData();
    }

    @Test
    public void testLoadMetrics() throws Exception {
        Map<Integer, InterfaceMetricI> interfaceMetrics = loadData.getInterfaceMetrics("1");
        Map<Integer, InterfaceMetricI> metrics = ServiceFactory.getMonitoring().getInterfaceMetrics(1);
        assertEquals(metrics, interfaceMetrics);
    }

    @Test
    public void testLoadMetricsNull() throws Exception {
        Map<Integer, InterfaceMetricI> interfaceMetrics = loadData.getInterfaceMetrics("5");
        assertNotNull(interfaceMetrics);
        assertTrue(interfaceMetrics.isEmpty());
    }

    @Test
    public void testLoadMetricsNotValiв() throws Exception {
        Map<Integer, InterfaceMetricI> interfaceMetrics = loadData.getInterfaceMetrics("asd");
        assertNotNull(interfaceMetrics);
        assertTrue(interfaceMetrics.isEmpty());
    }

    @Test
    public void testLoadLimit() throws Exception {
        InterfaceLimitI limit = loadData.getInterfaceLimit(2);
        assertEquals(limit.getCritical(), 150);
        assertEquals(limit.getWarning(), 100);
    }
}