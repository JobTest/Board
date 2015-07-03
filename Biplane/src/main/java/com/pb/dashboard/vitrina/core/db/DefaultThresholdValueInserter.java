package com.pb.dashboard.vitrina.core.db;

import com.pb.dashboard.core.db.ResourceNames;

public class DefaultThresholdValueInserter {

    private static final ResourceNames DB_DATE = ResourceNames.REDMINE;
    private static String tableName = "hourly_threshold";
    private static String bankIdColumn = "bank_id";
    private static String channelIdColumn = "channel_id";
    private static String hourColumn = "hour";
    private static String thresholdValueColumn = "threshold_value";

    private static String getInsertQuery() {
        return String.format("INSERT INTO %s(%s, %s, %s, %s) " +
                "VALUES(?, ?, ?, ?)",
                tableName, bankIdColumn, channelIdColumn, hourColumn, thresholdValueColumn);
    }

}