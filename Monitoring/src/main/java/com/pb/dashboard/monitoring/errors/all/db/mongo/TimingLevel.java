package com.pb.dashboard.monitoring.errors.all.db.mongo;

/**
 * Created by vlad
 * Date: 21.10.14_8:48
 */
public enum TimingLevel {

    QUERY("T0_INPUT_XML"),
    ANSWER("T0_OUTPUT_XML");

    private String name;

    private TimingLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
