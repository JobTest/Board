package com.pb.dashboard.vitrina.core.db;

import com.pb.dashboard.vitrina.payment.data.StatEnum;

import java.io.Serializable;

public class QueryBuilder implements Serializable {

    private String hourlyThresholdTable = "dashboard.hourly_threshold",
            bankTable = "dashboard.bank",
            channelTable = "dashboard.channel",

    id = "id",

    // bank table columns
    bankIdColumn = "bank_id",
            bankCountryColumn = "country",

    // channel table columns
    channelIdColumn = "channel_id",
            channelNameColumn = "name",

    // hourly_threshold table columns
    hourColumn = "hour",
            thresholdValueColumn = "threshold_value";

    private String channel1 = "OFFICE",
            channel2 = "TERMINAL",
            channel3 = "P24",
            channel4 = "3700";

    public String selectThreshold(String lang, int hour) {
        return String.format("SELECT %s FROM %s HT, %s B, %s C " +
                "WHERE B.%s = HT.%s AND " +
                "C.%s = HT.%s AND " +
                "B.%s = '%s' AND " +
                "(C.%s = '%s' OR " +
                "C.%s = '%s' OR " +
                "C.%s = '%s' OR " +
                "C.%s = '%s') AND " +
                "HT.%s = %d " +
                "ORDER BY C.%s;",
                thresholdValueColumn, hourlyThresholdTable, bankTable, channelTable,
                id, bankIdColumn, id, channelIdColumn,
                bankCountryColumn, convertToCountry(lang),
                channelNameColumn, channel1,
                channelNameColumn, channel2,
                channelNameColumn, channel3,
                channelNameColumn, channel4,
                hourColumn, hour,
                id);
    }

    public String getThresholdValue() {
        return thresholdValueColumn;
    }

    private String convertToCountry(String lang) {
        if (lang.equals("Россия")) return "Russia";
        if (lang.equals("Грузия")) return "Georgia";
        return "Ukraine";
    }

    private String convertToChannel(StatEnum stat) {
        if (stat == StatEnum.BASS) return "TERMINAL";
        if (stat == StatEnum.P24) return "P24";
        if (stat == StatEnum.P3700) return "3700";
        return "OFFICE";
    }
}
