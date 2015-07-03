package com.pb.dashboard.monitoring.timings.navigator;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.model.Country;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.monitoring.components.navigator.ContentItem;
import com.pb.dashboard.monitoring.components.navigator.NavigatorModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.pb.dashboard.monitoring.timings.navigator.NavigatorItem.*;

/**
 * Created by vlad
 * Date: 21.11.14_17:25
 */
public class NavigatorModelAdapter extends NavigatorModel {

    private Country country;
    private Complex complex;
    private DInterfaceI bpInterface;
    private Map<Integer, DInterfaceI> bpInterfaces;
    private InterfaceMetricI interfaceMetric;
    private Map<Integer, InterfaceMetricI> interfaceMetrics;

    public void setCountry(Country country) {
        this.country = country;
        ContentItem item = new ContentItem(country.getId(), country.getName());
        setContent(COUNTRY.getPkey(), item);
    }

    public Country getCountry() {
        return country;
    }

    public void setComplex(Complex complex) {
        this.complex = complex;
        ContentItem item = new ContentItem(complex.getId(), complex.getName());
        setContent(COMPLEX.getPkey(), item);
    }

    public Complex getComplex() {
        return complex;
    }

    public List<InterfaceMetricI> getMetrics() {
        return new ArrayList<>(interfaceMetrics.values());
    }

    public DInterfaceI getBpInterface() {
        ContentItem item = getItem(INTERFACE.getPkey());
        String pkey = item.getPkey();
        int id = Integer.parseInt(pkey);
        if (bpInterfaces.get(id) != null) {
            return bpInterfaces.get(id);
        }
        return bpInterface;
    }

    public InterfaceMetricI getMetric() {
        ContentItem item = getItem(METRIC.getPkey());
        String pkey = item.getPkey();
        int id = Integer.parseInt(pkey);
        if (interfaceMetrics.get(id) != null) {
            return interfaceMetrics.get(id);
        }
        return interfaceMetric;
    }

    public void setMetric(InterfaceMetricI metric) {
        this.interfaceMetric = metric;
        ContentItem item = new ContentItem(metric.getPkey(), metric.getDescription());
        setContent(METRIC.getPkey(), item);
    }

    public void setInterfaces(Map<Integer, DInterfaceI> bpInterfaces) {
        this.bpInterfaces = bpInterfaces;
        List<ContentItem> items = new ArrayList<ContentItem>();
        for (DInterfaceI bpInterface : bpInterfaces.values()) {
            items.add(new ContentItem(bpInterface.getPkey(), bpInterface.getDescription()));
        }
        setContents(INTERFACE.getPkey(), items);
    }

    public Map<Integer, DInterfaceI> getBpInterfaces() {
        return bpInterfaces;
    }

    public void setMetrics(Map<Integer, InterfaceMetricI> metrics) {
        this.interfaceMetrics = metrics;
        List<ContentItem> items = new ArrayList<ContentItem>();
        for (InterfaceMetricI metric : metrics.values()) {
            items.add(new ContentItem(metric.getPkey(), metric.getDescription()));
        }
        setContents(METRIC.getPkey(), items);
    }

    public void setBpInterface(DInterfaceI bpInterface) {
        this.bpInterface = bpInterface;
        ContentItem item = new ContentItem(bpInterface.getPkey(), bpInterface.getDescription());
        setContent(INTERFACE.getPkey(), item);
    }
}