package com.pb.dashboard.external.monitor.logic;

import com.pb.dashboard.external.monitor.entype.FilterRange;

import java.util.HashMap;
import java.util.Map;

public class ErrCntByInterfaceWrapper {
    HashMap<Integer, Map<String, Integer>> data = new HashMap<Integer, Map<String, Integer>>();

    public ErrCntByInterfaceWrapper() {
    }

    public boolean isContains(FilterRange filterRange, String date) {
        return (data.containsKey(getKey(filterRange,date)));
    }

    private Integer getKey(FilterRange filterRange, String date) {
        return filterRange.toString().hashCode()+date.toString().hashCode();
    }

    public Map<String, Integer> getData(FilterRange filterRange, String date) {
        return data.get(getKey(filterRange,date));
    }

    public void addData(FilterRange filterRange, String date, Map<String, Integer> map) {
        data.put(getKey(filterRange,date),map);
    }
}
