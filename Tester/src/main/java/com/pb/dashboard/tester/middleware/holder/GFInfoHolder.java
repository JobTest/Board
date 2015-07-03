package com.pb.dashboard.tester.middleware.holder;

import java.util.*;

public class GFInfoHolder {

    private String platform,
                   serverVersion,
                   port,
                   domain;
    private Map<String, List<String>> timeBuildMap; // String for deployment timestamp, List<String> for build names

    public GFInfoHolder(String platform, String serverVersion, String port, String domain) {
        this.platform = platform;
        this.serverVersion = serverVersion;
        this.port = port;
        this.domain = domain;
    }

    public void setTimeBuildMap(Map<String, List<String>> timeBuildMap) {
        this.timeBuildMap = timeBuildMap;
    }

    public String getPlatform() {
        return platform;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public String getPort() {
        return port;
    }

    public String getDomain() {
        return domain;
    }

    public Map<String, List<String>> getTimeBuildMap() {
        return timeBuildMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("Deployed apps:%n"));
        for (Map.Entry<String, List<String>> entry : timeBuildMap.entrySet()) {
            sb.append(String.format("  %s : ", entry.getKey()));
            for (String appName : entry.getValue()) {
                sb.append(appName + ", ");
            }
            sb.append(String.format("%n"));
        }
        return String.format("Platform: %s%nServer Version: %s%nPort: %s%nDomain: %s%n", platform, serverVersion, port, domain) + sb.toString();
    }

    public static void main(String[] args) {
        GFInfoHolder gfInfoHolder = new GFInfoHolder("PL-1", "GF-2", "4848", "domain1");
        Map<String, List<String>> apps = new HashMap<String, List<String>>();
        apps.put("2014.07.01", new ArrayList<String>(Arrays.asList("App1", "App2", "App3", "App4")));
        apps.put("2014.07.02", new ArrayList<String>(Arrays.asList("App1", "App2", "App3", "App4")));
        gfInfoHolder.setTimeBuildMap(apps);
    }

}
