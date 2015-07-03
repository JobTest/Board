package com.pb.dashboard.monitoring.errors.all.navigator;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.model.Country;
import com.pb.dashboard.core.util.UrlParamBuilder;
import com.pb.dashboard.dao.entity.biplanesupport.db.ErrorsTypeI;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.monitoring.components.navigator.ContentItem;
import com.pb.dashboard.monitoring.components.navigator.NavigatorModel;
import com.pb.dashboard.monitoring.components.parameter.MonitoringParam;
import com.pb.dashboard.monitoring.errors.all.db.container.SimpleObject;
import com.vaadin.server.Page;

import java.util.ArrayList;
import java.util.List;

import static com.pb.dashboard.monitoring.errors.all.navigator.NavigatorItem.*;

/**
 * Created by vlad
 * Date: 21.11.14_17:25
 */
public class NavigatorModelAdapter extends NavigatorModel {

    private Country country;
    private Complex complex;

    private ErrorsTypeI errorType;
    private List<ErrorsTypeI> errorTypes;

    private DInterfaceI bpInterface;
    private List<DInterfaceI> bpInterfaces;

    private SimpleObject group;
    private List<SimpleObject> groups;

    private SimpleObject responsible;
    private List<SimpleObject> responsibles;

    private SimpleObject consumer;
    private List<SimpleObject> consumers;

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

    public List<ErrorsTypeI> getMetrics() {
        return errorTypes;
    }

    public DInterfaceI getBpInterface() {
        ContentItem item = getItem(INTERFACE.getPkey());
        String pkey = item.getPkey();
        for (DInterfaceI bpInterface : bpInterfaces) {
            String id = String.valueOf(bpInterface.getPkey());
            if (pkey.equals(id)) {
                return bpInterface;
            }
        }
        return bpInterface;
    }

    public ErrorsTypeI getErrorType() {
        ContentItem item = getItem(METRIC.getPkey());
        String name = item.getName();
        for (ErrorsTypeI metric : errorTypes) {
            if (metric != null && metric.getName().equals(name)) {
                return metric;
            }
        }
        return errorType;
    }

    public void setErrorType(ErrorsTypeI metric) {
        this.errorType = metric;
        ContentItem item = new ContentItem(metric.getId(), metric.getName());
        setContent(METRIC.getPkey(), item);
    }

    public void setInterfaces(List<DInterfaceI> bpInterfaces) {
        this.bpInterfaces = bpInterfaces;
        List<ContentItem> items = new ArrayList<ContentItem>();
        for (DInterfaceI bpInterface : bpInterfaces) {
            items.add(new ContentItem(bpInterface.getPkey(), bpInterface.getDescription()));
        }
        setContents(INTERFACE.getPkey(), items);
    }

    public void setErrorTypes(List<ErrorsTypeI> metrics) {
        this.errorTypes = metrics;
        List<ContentItem> items = new ArrayList<ContentItem>();
        for (ErrorsTypeI metric : metrics) {
            items.add(new ContentItem(metric.getId(), metric.getName()));
        }
        setContents(METRIC.getPkey(), items);
    }

    public void setBpInterface(DInterfaceI bpInterface) {
        this.bpInterface = bpInterface;
        ContentItem item = new ContentItem(bpInterface.getPkey(), bpInterface.getDescription());
        setContent(INTERFACE.getPkey(), item);
    }

    public void setConsumers(List<SimpleObject> consumers) {
        this.consumers = consumers;
        setSimpleObjects(CONSUMER, consumers);
        if (!consumers.isEmpty()) {
            this.consumer = consumers.get(0);
        } else {
            this.consumer = new SimpleObject();
        }
    }

    public void setResponsibles(List<SimpleObject> responsibles) {
        this.responsibles = responsibles;
        setSimpleObjects(RESPONSIBLE, responsibles);
        if (!responsibles.isEmpty()) {
            responsible = responsibles.get(0);
        } else {
            responsible = new SimpleObject();
        }
    }

    public void setGroups(List<SimpleObject> groups) {
        this.groups = groups;
        setSimpleObjects(GROUP, groups);
        if (!groups.isEmpty()) {
            group = groups.get(0);
        } else {
            group = new SimpleObject();
        }
    }

    private void setSimpleObjects(NavigatorItem item, List<SimpleObject> objects) {
        List<ContentItem> list = new ArrayList<ContentItem>();
        for (SimpleObject object : objects) {
            list.add(new ContentItem(object.getId(), object.getName()));
        }
        setContents(item.getPkey(), list);
        if (!list.isEmpty()) {
            setContent(item.getPkey(), list.get(0));
        }
    }

    public SimpleObject getGroup() {
        return group;
    }

    public SimpleObject getResponsible() {
        return responsible;
    }

    public SimpleObject getConsumer() {
        return consumer;
    }

    public void setItem(int navigatorId, String pkey) {
        if (navigatorId == NavigatorItem.GROUP.getPkey()) {
            group = getSimpleObject(groups, pkey);
        } else if (navigatorId == NavigatorItem.RESPONSIBLE.getPkey()) {
            responsible = getSimpleObject(responsibles, pkey);
        } else if (navigatorId == NavigatorItem.CONSUMER.getPkey()) {
            consumer = getSimpleObject(consumers, pkey);
        }
    }

    private SimpleObject getSimpleObject(List<SimpleObject> objs, String pkey) {
        for (SimpleObject obj : objs) {
            String id = obj == null ? null : String.valueOf(obj.getId());
            if (pkey.equals(id)) {
                return obj;
            }
        }
        return new SimpleObject();
    }

    public void changeUrlParams() {
        String uriFragment = Page.getCurrent().getUriFragment().split("\\?")[0];
        UrlParamBuilder builder = new UrlParamBuilder();
        builder.addParam(MonitoringParam.COUNTRY.getName(), getCountry().getId())
                .addParam(MonitoringParam.COMPLEX.getName(), getComplex().getId())
                .addParam(MonitoringParam.TYPE.getName(), getErrorType().getName())
                .addParam(MonitoringParam.INTERFACE.getName(), getBpInterface().getPkey());
        String allUriFragment = uriFragment + builder.toString();
        Page.getCurrent().setUriFragment(allUriFragment, false);
    }
}