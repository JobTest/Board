package com.pb.dashboard.monitoring.timings.navigator;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.model.Country;
import com.pb.dashboard.core.util.IntegerUtil;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.monitoring.components.parameter.MonitoringParam;

import java.util.Map;

/**
 * Created by vlad
 * Date: 20.11.14_14:57
 */
public final class NavigatorModelBuilder {

    private static final int COMPONENTS_FIRST_LINE = 4;
    private NavigatorModelAdapter model;

    public static NavigatorModelAdapter build(Map<String, String> map) {
        return new NavigatorModelBuilder(map).getModel();
    }

    private NavigatorModelBuilder(Map<String, String> map) {
        model = new NavigatorModelAdapter();
        initCountry(getValue(map, MonitoringParam.COUNTRY));
        initComplex(getValue(map, MonitoringParam.COMPLEX));
        initInterface(getValue(map, MonitoringParam.INTERFACE));
        initMetric(getValue(map, MonitoringParam.METRIC));
        model.setPointsNewLine(COMPONENTS_FIRST_LINE);
    }

    private void initCountry(String value) {
        Country country;
        try {
            country = Country.pkeyToCountry(value);
        } catch (Exception e) {
            country = Country.UKRAINE;
        }
        model.setCountry(country);
    }

    private void initComplex(String value) {
        Complex complex;
        try {
            complex = Complex.pkeyToComplex(value);
        } catch (Exception e) {
            complex = Complex.BIPLANE_API2X;
        }
        model.setComplex(complex);
    }

    private void initInterface(String value) {
        Map<Integer, DInterfaceI> interfaces = loadInterfaces(model.getCountry(), model.getComplex());
        model.setInterfaces(interfaces);

        if (IntegerUtil.checkInt(value)) {
            int pkey = Integer.parseInt(value);
            model.setBpInterface(interfaces.get(pkey));
            return;
        }
        if (!interfaces.isEmpty()) {
            model.setBpInterface(interfaces.values().iterator().next());
        }
    }

    private Map<Integer, DInterfaceI> loadInterfaces(Country country, Complex complex) {
        return ServiceFactory.getMonitoring().getInterfaces(complex.getId(), country.getId());
    }

    private void initMetric(String value) {
        int interfacePkey = model.getBpInterface().getPkey();
        Map<Integer, InterfaceMetricI> metrics = loadMetrics(interfacePkey);
        model.setMetrics(metrics);

        if (IntegerUtil.checkInt(value)) {
            int pkey = Integer.parseInt(value);
            model.setMetric(metrics.get(pkey));
            return;
        }
        if (!metrics.isEmpty()) {
            model.setMetric(metrics.values().iterator().next());
        }
    }

    private Map<Integer, InterfaceMetricI> loadMetrics(int interfacePkey) {
        return ServiceFactory.getMonitoring().getInterfaceMetrics(interfacePkey);
    }

    private String getValue(Map<String, String> map, MonitoringParam param) {
        if (map == null || map.isEmpty() || param == null) {
            return null;
        }
        return map.get(param.getName());
    }

    public NavigatorModelAdapter getModel() {
        return model;
    }
}