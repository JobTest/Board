package com.pb.dashboard.core.db.mongo.datasource;

import com.pb.dashboard.core.db.mongo.DMongoCollection;
import com.pb.dashboard.core.db.mongo.client.DMongoClientI;

import java.util.Objects;

/**
 * Created by vlad
 * Date: 05.12.14_10:01
 */
public class DMongoDatasourceCustom implements DMongoDatasourceI {

    private static final String MONGO_DB = "dashboard";
    private DMongoClientI dMongoClient;
    private DMongoCollection mongoCollection;

    public DMongoDatasourceCustom(DMongoClientI dMongoClient, DMongoCollection mongoCollection) {
        this.dMongoClient = dMongoClient;
        this.mongoCollection = mongoCollection;
    }

    @Override
    public String getDb() {
        return MONGO_DB;
    }

    @Override
    public String getCollection() {
        return mongoCollection.getName();
    }

    @Override
    public DMongoClientI getDMongoClient() {
        return dMongoClient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dMongoClient, mongoCollection);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DMongoDatasourceCustom other = (DMongoDatasourceCustom) obj;
        return Objects.equals(this.dMongoClient, other.dMongoClient)
                && Objects.equals(this.mongoCollection, other.mongoCollection);
    }
}