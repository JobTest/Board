package com.pb.dashboard.monitoring.errors.all.navigator;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.model.Country;
import com.pb.dashboard.dao.entity.biplanesupport.db.ErrorsType;
import com.pb.dashboard.dao.entity.biplanesupport.db.ErrorsTypeI;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterface;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.monitoring.components.parameter.MonitoringParam;
import com.pb.dashboard.monitoring.errors.all.db.container.SimpleObject;
import com.pb.dashboard.monitoring.errors.all.db.pl.PLDBManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 20.11.14_14:57
 */
public final class NavigatorModelBuilder {

    private static final String ALL_INTERFACE = "Все интерфейсы";
    private static final String ALL_GROUP = "Все группы";
    private static final String ALL_RESPONSIBLE = "Все ответственные";
    private static final String ALL_CONSUMER = "Все потребители";

    public static final SimpleObject ALL_INTERFACE_INTERFACE = new SimpleObject(null, ALL_INTERFACE);
    public static final SimpleObject ALL_GROUP_GROUP = new SimpleObject(null, ALL_GROUP);
    public static final SimpleObject ALL_RESPONSIBLE_RESPONSIBLE = new SimpleObject(null, ALL_RESPONSIBLE);
    public static final SimpleObject ALL_CONSUMER_CONSUMER = new SimpleObject(null, ALL_CONSUMER);

    private static final Logger log = Logger.getLogger(NavigatorModelBuilder.class);
    private NavigatorModelAdapter model;

    public static NavigatorModelAdapter build(Map<String, String> map) {
        return new NavigatorModelBuilder(map).getModel();
    }

    private NavigatorModelBuilder(Map<String, String> map) {
        model = new NavigatorModelAdapter();
        initCountry(getValue(map, MonitoringParam.COUNTRY));
        initComplex(getValue(map, MonitoringParam.COMPLEX));
        initType(getValue(map, MonitoringParam.TYPE));
        initInterface();
        initGroups();
        initResponsibles();
        initConsumers();
        model.setPointsNewLine(4);
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

    private void initType(String value) {
        List<ErrorsTypeI> types = getTypesErrors(model.getCountry(), model.getComplex());
        model.setErrorTypes(types);

        for (ErrorsTypeI type : types) {
            if (type.getName().equals(value)) {
                model.setErrorType(type);
                return;
            }
        }
        if (!types.isEmpty()) {
            model.setErrorType(types.get(0));
        }
    }

    public static List<ErrorsTypeI> getTypesErrors(Country country, Complex complex) {
        List<ErrorsTypeI> types = loadTypes();
        if (country == Country.UKRAINE) {
            if (complex == Complex.BIPLANE_API2X
                    || complex == Complex.DEBT
                    || complex == Complex.TEMPLATES) {
                types.add(0, ErrorsType.ALL);
                if (complex == Complex.BIPLANE_API2X) {
                    types.add(1, ErrorsType.INPUT_USER);
                }
                if (complex == Complex.BIPLANE_API2X) {
                    types.add(ErrorsType.RECIPIENT);
                }
            }
        }
        return types;
    }

    private static List<ErrorsTypeI> loadTypes() {
        return PLDBManager.getInstance().getTypes();
    }

    private void initInterface() {
        List<DInterfaceI> interfaces = getInterfaces(model.getCountry(), model.getComplex());
        model.setInterfaces(interfaces);

        if (!interfaces.isEmpty()) {
            model.setBpInterface(interfaces.get(0));
        }
    }

    public List<DInterfaceI> getInterfaces(Country country, Complex complex) {
        List<DInterfaceI> bpInterfaces = loadInterfaces(country, complex);
        bpInterfaces.add(0, convertInterface(ALL_INTERFACE_INTERFACE));
        return bpInterfaces;
    }

    private static DInterfaceI convertInterface(SimpleObject simpleObject) {
        return new DInterface(simpleObject.getId(), "", simpleObject.getName());
    }

    private static List<DInterfaceI> loadInterfaces(Country country, Complex complex) {
        return new ArrayList<>(ServiceFactory.getMonitoring().getInterfaces(complex.getId(), country.getId()).values());
    }

    private void initGroups() {
        List<SimpleObject> groups = PLDBManager.getInstance().getGroups();
        groups.add(0, ALL_GROUP_GROUP);
        model.setGroups(groups);
    }

    private void initResponsibles() {
        List<SimpleObject> responsibility = PLDBManager.getInstance().getResponsibility();
        responsibility.add(0, ALL_RESPONSIBLE_RESPONSIBLE);
        model.setResponsibles(responsibility);
    }

    private void initConsumers() {
        List<SimpleObject> consumers = PLDBManager.getInstance().getConsumers();
        consumers.add(0, ALL_CONSUMER_CONSUMER);
        model.setConsumers(consumers);
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