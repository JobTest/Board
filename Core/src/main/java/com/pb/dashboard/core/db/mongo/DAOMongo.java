package com.pb.dashboard.core.db.mongo;

import com.mongodb.*;
import com.pb.dashboard.core.db.mongo.client.DMongoClientI;
import com.pb.dashboard.core.db.mongo.datasource.DMongoDatasourceI;
import com.pb.dashboard.core.error.DashSQLException;
import com.pb.dashboard.core.error.ErrorCode;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DAOMongo {

    private static final int TIMEOUT = 5000;
    private static Map<DMongoClientI, MongoClient> clients = new HashMap<DMongoClientI, MongoClient>();

    public abstract Logger getLog();

    private static DBCollection getCollection(DMongoDatasourceI mongoDB) throws DashSQLException {
        MongoClient client = getMongoClient(mongoDB.getDMongoClient());
        DB db = client.getDB(mongoDB.getDb());
        return db.getCollection(mongoDB.getCollection());
    }

    private static MongoClient getMongoClient(DMongoClientI dMongoClient) throws DashSQLException {
        MongoClient client = clients.get(dMongoClient);
        if (client == null) {
            client = open(dMongoClient);
        }
        return client;
    }

    private static MongoClient open(DMongoClientI dMongoClient) throws DashSQLException {
        try {
            MongoClientOptions build = MongoClientOptions.builder().socketTimeout(TIMEOUT).build();
            ServerAddress address = new ServerAddress(dMongoClient.getHost(), dMongoClient.getPort());
            MongoClient mongoClient = new MongoClient(address, build);
            clients.put(dMongoClient, mongoClient);
            return mongoClient;
        } catch (UnknownHostException e) {
            throw new DashSQLException(e.getMessage(), ErrorCode.SQL.CONNECT_NOT_CREATED);
        }
    }

    public List<DBObject> execList(DMongoDatasourceI ds, BasicDBObject findObject) throws DashSQLException {
        try {
            return getList(ds, findObject, null);
        } catch (Exception e) {
            getLog().error(e.getMessage(), e);
            open(ds.getDMongoClient());
            return getList(ds, findObject, null);
        }
    }

    public List<DBObject> execList(DMongoDatasourceI ds, BasicDBObject findObject, BasicDBObject fields) throws DashSQLException {
        try {
            return getList(ds, findObject, fields);
        } catch (Exception e) {
            getLog().error(e.getMessage(), e);
            open(ds.getDMongoClient());
            return getList(ds, findObject, fields);
        }
    }

    private List<DBObject> getList(DMongoDatasourceI ds, BasicDBObject findObject, BasicDBObject fields) throws DashSQLException {
        List<DBObject> result = new ArrayList<DBObject>();
        DBCursor cursor = null;
        try {
            DBCollection collection = getCollection(ds);
            long t1 = System.nanoTime();
            if (fields != null) {
                cursor = collection.find(findObject, fields);
            } else {
                cursor = collection.find(findObject);
            }
            long t2 = System.nanoTime();

            while (cursor.hasNext()) {
                DBObject next = cursor.next();
                result.add(next);
            }
            getLog().debug("Query: " + findObject + "  DB: " + ds.getDb() + "  Collection: " + ds.getCollection() + "  Time: " + ((t2 - t1) / 1000000) + " mc.");
        } catch (Exception e) {
            throw new DashSQLException(ErrorCode.SQL.ERROR_EXECUTE, findObject.toString(), e.getMessage());
        } finally {
            cursor.close();
        }
        return result;
    }

    public DBObject execOne(DMongoDatasourceI ds, BasicDBObject findObject) throws DashSQLException {
        try {
            return getOne(ds, findObject, null);
        } catch (Exception e) {
            getLog().error(e.getMessage(), e);
            open(ds.getDMongoClient());
            return getOne(ds, findObject, null);
        }
    }

    public DBObject execOne(DMongoDatasourceI ds, BasicDBObject findObject, BasicDBObject fields) throws DashSQLException {
        try {
            return getOne(ds, findObject, fields);
        } catch (Exception e) {
            open(ds.getDMongoClient());
            return getOne(ds, findObject, fields);
        }
    }

    private DBObject getOne(DMongoDatasourceI ds, BasicDBObject findObject, BasicDBObject fields) throws DashSQLException {
        try {
            DBCollection collection = getCollection(ds);
            DBObject dbObject;
            long t1 = System.nanoTime();
            if (fields == null) {
                dbObject = collection.findOne(findObject);
            } else {
                dbObject = collection.findOne(findObject, fields);
            }
            long t2 = System.nanoTime();
            getLog().debug("Query: " + findObject + "  DB: " + ds.getDb() + "  Collection: " + ds.getCollection() + "  Time: " + ((t2 - t1) / 1000000) + " ms.");
            return dbObject;
        } catch (Exception e) {
            throw new DashSQLException(ErrorCode.SQL.ERROR_EXECUTE, findObject.toString());
        }
    }

    public BasicDBObject createFields(String... fields) {
        BasicDBObject dbObject = new BasicDBObject();
        for (String field : fields) {
            dbObject.put(field, true);
        }
        return dbObject;
    }
}