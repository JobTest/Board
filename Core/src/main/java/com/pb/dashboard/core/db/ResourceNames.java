package com.pb.dashboard.core.db;

public enum ResourceNames {

    VITRINA_METRICS("java:comp/env/jdbc/vitrinaMetrics"),
    BIPLANE_SUPPORT_DB("java:comp/env/jdbc/biplaneSupportDB"),
    DASHBOARD("java:comp/env/jdbc/dashboard"),
    ISSUES("java:comp/env/jdbc/issues"),
    REDMINE("java:comp/env/jdbc/redmine"),
    TESTER("java:comp/env/jdbc/tester"),
    DESCRIPTION("java:comp/env/jdbc/description"),
    TRANSCRIPT("java:comp/env/jdbc/transcript");

    private String name;

    ResourceNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}