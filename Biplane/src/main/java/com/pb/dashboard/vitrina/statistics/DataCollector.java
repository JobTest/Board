package com.pb.dashboard.vitrina.statistics;

public class DataCollector {

    private Object data;
    private Object total;

    public DataCollector(Object data, Object total) {
        this.data = data;
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public Object getTotal() {
        return total;
    }
}
