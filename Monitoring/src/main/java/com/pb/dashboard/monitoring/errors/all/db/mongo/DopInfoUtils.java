package com.pb.dashboard.monitoring.errors.all.db.mongo;

/**
 * Created by vlad
 * Date: 05.12.14_13:41
 */
public final class DopInfoUtils {
    private static final String SYMBOLS = "\\@\\#\\*\\$";
    private static final String NEW_LINE_SYMBOL = "\n";

    private DopInfoUtils() {
    }

    public static String convert(String text) {
        return text.replaceAll(SYMBOLS, NEW_LINE_SYMBOL);
    }
}