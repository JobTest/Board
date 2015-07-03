package com.pb.dashboard.core.db.mongo.client;

/**
 * Created by vlad
 * Date: 05.12.14_11:56
 */
public enum DMongoClient implements DMongoClientI {

    H10_13_98_118_P40000("10.13.98.118", 40000),
    H10_13_98_118_P30000("10.13.98.118", 30000),
    H10_13_98_173_P40000("10.13.98.173", 40000),
    H10_13_70_76_P40000("10.13.70.76", 40000);

    private String host;
    private int port;

    DMongoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    public static DMongoClientI getDMongoClient(DMongoClientI dMongoClient) {
        for (DMongoClientI client : DMongoClient.values()) {
            if (client.getHost().equals(dMongoClient.getHost()) && client.getPort() == dMongoClient.getPort()) {
                return client;
            }
        }
        throw new IllegalArgumentException(dMongoClient.getHost() + ":" + dMongoClient.getPort() + " is not valid");
    }

    @Override
    public String toString() {
        return "[host='" + host + '\'' +
                ", port=" + port +
                "]";
    }
}
