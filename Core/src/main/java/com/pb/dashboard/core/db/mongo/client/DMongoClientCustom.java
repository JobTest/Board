package com.pb.dashboard.core.db.mongo.client;

import java.util.Objects;

/**
 * Created by vlad
 * Date: 05.12.14_12:29
 */
public class DMongoClientCustom implements DMongoClientI {

    private String host;
    private int port;

    public DMongoClientCustom(String host, int port) {
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

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DMongoClientCustom other = (DMongoClientCustom) obj;
        return Objects.equals(this.host, other.host)
                && Objects.equals(this.port, other.port);
    }
}
