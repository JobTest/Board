package com.pb.dashboard.tester.middleware.holder;

public class EASInfoHolder {

    private String baseURL,
            port,
            dbIP,
            dbName;

    private String[] apps;

    public EASInfoHolder(String baseURL) {
        this.baseURL = baseURL;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setDbIP(String dbIP) {
        this.dbIP = dbIP;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setApps(String[] apps) {
        this.apps = apps;
    }

    public String getPort() {
        return port;
    }

    public String getDbIP() {
        return dbIP;
    }

    public String getDbName() {
        return dbName;
    }

    public String[] getApps() {
        return apps;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("Deployed apps:%n"));
        for (String name : apps) {
            sb.append(String.format("  %s%n", name));
        }
        return String.format("EAS: %s%nPort: %s%ndbIP: %s%ndbName: %s%n", baseURL, port, dbIP, dbName) + sb.toString();
    }
}
