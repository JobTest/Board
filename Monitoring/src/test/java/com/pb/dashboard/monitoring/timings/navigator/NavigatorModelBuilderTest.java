package com.pb.dashboard.monitoring.timings.navigator;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.model.Country;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.service.MonitoringServiceMock;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.monitoring.components.parameter.MonitoringParam;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class NavigatorModelBuilderTest {

    private Map<String, String> map = new LinkedHashMap<>();

    @InjectMocks
    private ServiceFactory factory;
    @Spy
    private MonitoringServiceMock service;

    @BeforeClass
    public static void up() throws Exception {
        new ClassPathXmlApplicationContext("/monitoring-context.xml");
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testParamsInvalid() throws Exception {
        addParam(MonitoringParam.COUNTRY, "invalid");
        addParam(MonitoringParam.COMPLEX, "invalid");
        addParam(MonitoringParam.INTERFACE, "invalid");
        addParam(MonitoringParam.METRIC, "invalid");
        testParamsEmpty();
    }

    @Test
    public void testParamsEmpty() throws Exception {
        NavigatorModelAdapter model = NavigatorModelBuilder.build(map);
        assertEquals(Country.UKRAINE, model.getCountry());
        assertEquals(Complex.BIPLANE_API2X, model.getComplex());
        Map<Integer, DInterfaceI> interfaces = ServiceFactory.getMonitoring().getInterfaces(0, 0);
        assertEquals(interfaces, model.getBpInterfaces());
        assertEquals(interfaces.values().iterator().next(), model.getBpInterface());
        ArrayList<InterfaceMetricI> metrics = new ArrayList<>(ServiceFactory.getMonitoring().getInterfaceMetrics(0).values());
        assertEquals(metrics, model.getMetrics());
        assertEquals(metrics.get(0), model.getMetric());
    }

    @Test
    public void testParamCountry() throws Exception {
        Country country = Country.RUSSIA;
        addParam(MonitoringParam.COUNTRY, country.getId());
        NavigatorModelAdapter model = NavigatorModelBuilder.build(map);
        assertEquals(country, model.getCountry());
    }

    @Test
    public void testParamComplex() throws Exception {
        Complex complex = Complex.DEBT;
        addParam(MonitoringParam.COMPLEX, complex.getId());
        NavigatorModelAdapter model = NavigatorModelBuilder.build(map);
        assertEquals(complex, model.getComplex());
    }

    @Test
    public void testParamCountryComplex() throws Exception {
        Country country = Country.RUSSIA;
        Complex complex = Complex.DEBT;
        addParam(MonitoringParam.COUNTRY, country.getId());
        addParam(MonitoringParam.COMPLEX, complex.getId());
        NavigatorModelBuilder.build(map);
        verify(ServiceFactory.getMonitoring()).getInterfaces(complex.getId(), country.getId());
    }

    @Test
    public void testParamInterface() throws Exception {
        Integer interfaceId = 7;
        addParam(MonitoringParam.INTERFACE, interfaceId);
        NavigatorModelAdapter model = NavigatorModelBuilder.build(map);
        assertEquals(interfaceId, model.getBpInterface().getPkey());
    }

    @Test
    public void testParamMetrics() throws Exception {
        int interfaceId = 8;
        addParam(MonitoringParam.INTERFACE, interfaceId);
        NavigatorModelBuilder.build(map);
        verify(ServiceFactory.getMonitoring()).getInterfaceMetrics(interfaceId);
    }

    @Test
    public void testParamMetric() throws Exception {
        int metricId = 3;
        addParam(MonitoringParam.METRIC, metricId);
        NavigatorModelAdapter model = NavigatorModelBuilder.build(map);
        assertEquals(metricId, model.getMetric().getPkey());
    }

    private void addParam(Object key, Object value) {
        map.put(String.valueOf(key), String.valueOf(value));
    }
}