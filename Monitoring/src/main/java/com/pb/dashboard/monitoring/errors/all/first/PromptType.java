package com.pb.dashboard.monitoring.errors.all.first;

/**
 * Created by vlad
 * Date: 13.01.15_13:27
 */
public enum PromptType {

    SESSION("id_session"),
    COMPANY("id_company");

    private final String name;

    PromptType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
