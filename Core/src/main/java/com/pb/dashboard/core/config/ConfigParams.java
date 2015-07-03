package com.pb.dashboard.core.config;

/**
 * Created by vlad
 * Date: 09.02.15_14:53
 */
public enum ConfigParams {

    MONGO_HOST("mongoHost"),
    MONGO_PORT("mongoPort"),
    WEB_SERVER_URL("webServerUrl"),
    WEB_CLIENT_URL("webClientUrl"),
    DASHBOARD_URL("dashboardUrl");

    private final String name;

    ConfigParams(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
