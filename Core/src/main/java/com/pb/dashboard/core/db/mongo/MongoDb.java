package com.pb.dashboard.core.db.mongo;

/**
 * Created by vlad
 * Date: 21.10.14_17:22
 */
public enum MongoDb {

    API("api_2x"),
    DEBT("debt"),
    TEMPLATE("template");

    private String name;

    private MongoDb(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
