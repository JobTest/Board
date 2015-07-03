package com.pb.dashboard.core.db.mongo.datasource;

import com.pb.dashboard.core.db.mongo.client.DMongoClient;
import com.pb.dashboard.core.db.mongo.client.DMongoClientI;

public enum DMongoDatasource implements DMongoDatasourceI {

    LOGSTASH_LOGS(DMongoClient.H10_13_98_118_P40000, "logstash", "logs"),
    DEBT_LOGS(DMongoClient.H10_13_98_118_P30000, "debt", "logs"),
    DEBT_ERROR(DMongoClient.H10_13_98_118_P30000, "debt", "error"),
    ERRORS_ERROR(DMongoClient.H10_13_98_118_P30000, "errors", "error"),
    TIMING_T2_T2_LOGS(DMongoClient.H10_13_98_118_P30000, "timing_t2", "t2_logs"),
    TEMPLATE_LOGS(DMongoClient.H10_13_98_173_P40000, "template", "logs"),
    TEMPLATE_ERROR(DMongoClient.H10_13_98_173_P40000, "template", "error"),
    TEMPLATE_TIMING_T2(DMongoClient.H10_13_98_173_P40000, "template", "timing_t2"),
    API_TIMING_T0(DMongoClient.H10_13_70_76_P40000, "API", "timing_t0");

    private DMongoClientI dMongoClient;
    private String db;
    private String collection;

    DMongoDatasource(DMongoClient dMongoClient, String db, String collection) {
        this.dMongoClient = dMongoClient;
        this.db = db;
        this.collection = collection;
    }

    @Override
    public String getDb() {
        return db;
    }

    @Override
    public String getCollection() {
        return collection;
    }

    @Override
    public DMongoClientI getDMongoClient() {
        return dMongoClient;
    }
}