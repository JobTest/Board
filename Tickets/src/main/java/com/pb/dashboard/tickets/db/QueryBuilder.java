package com.pb.dashboard.tickets.db;

public final class QueryBuilder {

    private QueryBuilder() {
    }

    public static String getChannelsOutputQuery() {
        return "select id, name from channel_output";
    }

    public static String getCountSalesByChannelOutput() {
        return "call getCountSalesByChannel(?, ?, ?, ?)";
    }

    public static String getCountSalesByTicketType() {
        return "call getCountSalesByTicketType(?, ?, ?)";
    }

    public static String getCountSalesByBranch() {
        return "call getCountSalesByBranch(?, ?, ?, ?, ?)";
    }

    public static String getCountSalesByMonth() {
        return "call getCountSalesByMonth(?, ?, ?)";
    }
}