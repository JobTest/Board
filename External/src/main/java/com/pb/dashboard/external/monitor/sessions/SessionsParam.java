package com.pb.dashboard.external.monitor.sessions;

/**
 * Created by vlad
 * Date: 11.11.14_12:01
 */
public enum SessionsParam {

    COMPLEX("complex"),
    SYSTEM("system"),
    RANGE("range"),
    DATE("date"),
    INTERFACE("interface");

    private String name;

    private SessionsParam(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
