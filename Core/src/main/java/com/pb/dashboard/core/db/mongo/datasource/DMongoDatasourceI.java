package com.pb.dashboard.core.db.mongo.datasource;

import com.pb.dashboard.core.db.mongo.client.DMongoClientI;

/**
 * Created by vlad
 * Date: 05.12.14_9:58
 */
public interface DMongoDatasourceI {

    public String getDb();

    public String getCollection();

    public DMongoClientI getDMongoClient();
}
