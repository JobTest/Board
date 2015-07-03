package com.pb.dashboard.server.dao.log;

/**
 * Created by vlad
 * Date: 06.05.15_12:13
 */
public class LogUtils {

    public static String formatQueryLog(String query, Object... params){
        StringBuilder debug = new StringBuilder("Query: ");
        debug.append(query);
        try {
            for (Object p : params) {
                addParam(debug, p);
            }
        } catch (Exception e) {
        }
        return debug.toString();
    }

    private static void addParam(StringBuilder sb, Object p) {
        String querySymbol = "?";
        int begin = sb.indexOf(querySymbol);
        if (begin == -1) {
            return;
        }
        int end = begin + querySymbol.length();
        String param = String.valueOf(p);
        if (p instanceof String) {
            sb.replace(begin, end, '\'' + param + '\'');
        } else {
            sb.replace(begin, end, param);
        }
    }

}
