package com.pb.dashboard.quality.db;

import com.pb.dashboard.core.model.Bank;
import com.pb.dashboard.quality.types.Channel;
import com.pb.dashboard.quality.types.PaymentType;
import com.pb.dashboard.quality.types.ValueType;

public class QueryBuilder {

    // table name
    private final String table = "pq_items";

    // column names
    private final String bank = "bank_id";
    private final String channelColumn = "channel_id";
    private final String valueTypeColumn = "value_type";
    private final String paymentTypeColumn = "payment_type";
    private final String rejectName = "reject_name";
    private final String date = "date";
    private final String value = "value";

    private final String totalValue = "total_value";
    private final String avgTime = "avg_time";

    public String selectItemsByPeriod(Bank newBank, ValueType valueType, Channel channel, PaymentType paymentType,
                                      String dateFrom, String dateTo) {
        StringBuilder sb = new StringBuilder(String.format("SELECT * FROM %s " +                       // all items
                "WHERE %s = (SELECT id FROM bank WHERE attr_id = '%s') " +
                "AND %s = '%s' " +
                "AND %s = (SELECT id FROM channel WHERE attr_id = '%s') ",
                table, bank, newBank.getAttrName(),
                valueTypeColumn, valueType.toString(),
                channelColumn, channel.getAttrId()));
        if (paymentType != PaymentType.ALL)
            sb.append(String.format("AND %s = '%s' ", paymentTypeColumn, paymentType.getAttrID()));   // paymentType
        sb.append(String.format("AND %s >='%s' AND %s <='%s';", date, dateFrom, date, dateTo));      //dates
        return sb.toString();
    }

    public String selectSumValue(Bank newBank, ValueType valueType, Channel channel, PaymentType paymentType,
                                 String dateFrom, String dateTo) {
        StringBuilder sb = new StringBuilder(String.format("SELECT SUM(%s) AS %s FROM %s " +      // SUM as totalValue
                "WHERE %s = (SELECT id FROM bank WHERE attr_id = '%s') " +
                "AND %s = '%s' " +
                "AND %s = (SELECT id FROM channel WHERE attr_id = '%s') ",
                value, totalValue, table,
                bank, newBank.getAttrName(),
                valueTypeColumn, valueType.toString(),
                channelColumn, channel.getAttrId()));
        if (paymentType != PaymentType.ALL)
            sb.append(String.format("AND %s = '%s' ", paymentTypeColumn, paymentType.getAttrID()));   // paymentType
        sb.append(String.format("AND %s >='%s' AND %s <='%s';", date, dateFrom, date, dateTo));      //dates
        return sb.toString();
    }

    public String selectTotalValueByChannel(Bank newBank, ValueType valueType, Channel channel,
                                            String dateFrom, String dateTo) {
        return String.format("SELECT %s, SUM(%s) AS %s FROM %s " +                     // date, SUM(value) as totalValue
                "WHERE %s = (SELECT id FROM bank WHERE attr_id = '%s') " +
                "AND %s = '%s' " +
                "AND %s = (SELECT id FROM channel WHERE attr_id = '%s') " +
                "AND %s >='%s' " +
                "AND %s <='%s' " +                                     // dates
                "GROUP BY %s;",                                                     // group by date
                date, value, totalValue, table,
                bank, newBank.getAttrName(),
                valueTypeColumn, valueType.toString(),
                channelColumn, channel.getAttrId(),
                date, dateFrom,
                date, dateTo,
                date);
    }

    public String selectValueByDate(Bank newBank, ValueType valueType, Channel channel, PaymentType paymentType,
                                    String dateFrom, String dateTo) {
        StringBuilder sb = new StringBuilder(String.format("SELECT %s, SUM(%s) AS %s FROM %s " +   // date, SUM as totalValue
                "WHERE %s = (SELECT id FROM bank WHERE attr_id = '%s') " +
                "AND %s = '%s' " +
                "AND %s = (SELECT id FROM channel WHERE attr_id = '%s') ",                                     // bank, valueType, channel
                date, value, totalValue, table,
                bank, newBank.getAttrName(),
                valueTypeColumn, valueType.toString(),
                channelColumn, channel.getAttrId()));
        if (paymentType != PaymentType.ALL)
            sb.append(String.format("AND %s = '%s' ", paymentTypeColumn, paymentType.getAttrID()));    // paymentType
        sb.append(String.format("AND %s >='%s' AND %s <='%s' GROUP BY %s;", date, dateFrom,           // dates
                date, dateTo, date));                                                                // group by date
        return sb.toString();
    }

    public String selectAvgProcessingTime(Bank newBank, ValueType valueType, Channel channel, PaymentType paymentType,
                                          String dateFrom, String dateTo) {
        StringBuilder sb = new StringBuilder(String.format("SELECT AVG(%s) AS %s FROM %s " +  // AVG as avgTime
                "WHERE %s = (SELECT id FROM bank WHERE attr_id = '%s') " +
                "AND %s = '%s' " +
                "AND %s = (SELECT id FROM channel WHERE attr_id = '%s') ",                                // bank, valueTYpe, channel
                value, avgTime, table, bank, newBank.getAttrName(), valueTypeColumn,
                valueType.toString(), channelColumn, channel.getAttrId()));
        if (paymentType != PaymentType.ALL)
            sb.append(String.format("AND %s = '%s' ", paymentTypeColumn, paymentType.getAttrID()));   // paymentType
        sb.append(String.format("AND %s >='%s' AND %s <='%s';", date, dateFrom, date, dateTo));      //dates
        return sb.toString();
    }

    public String getTable() {
        return table;
    }

    public String getBank() {
        return bank;
    }

    public String getChannel() {
        return channelColumn;
    }

    public String getValueType() {
        return valueTypeColumn;
    }

    public String getPaymentType() {
        return paymentTypeColumn;
    }

    public String getRejectName() {
        return rejectName;
    }

    public String getDate() {
        return date;
    }

    public String getValue() {
        return value;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public String getAvgTime() {
        return avgTime;
    }
}
