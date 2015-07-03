package com.pb.dashboard.monitoring.errors.all.window.complex;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.dao.entity.biplanesupport.db.DObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 22.09.14
 */
public class ComplexModel<T extends DObject> {

    private String sessionId;
    private Map<String, List<T>> data;
    private Complex complexFrom;
    private Complex complex;
    private boolean load;

    public ComplexModel() {
        data = new LinkedHashMap<String, List<T>>();
    }

    public Complex getComplexFrom() {
        return complexFrom;
    }

    public void setComplexFrom(Complex complexFrom) {
        this.complexFrom = complexFrom;
    }

    public void setComplex(Complex complex) {
        this.complex = complex;
    }

    public Complex getComplex() {
        return complex;
    }

    public boolean isLoad() {
        return load;
    }

    public void setLoad(boolean load) {
        this.load = load;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Map<String, List<T>> getMapInfo() {
        return data;
    }

    public void addListInfo(List<T> listInfo) {
        for (T info : listInfo) {
            String id = info.getId();
            if (!data.containsKey(id)) {
                data.put(id, new ArrayList<T>());
            }
            data.get(id).add(info);
        }
    }
}