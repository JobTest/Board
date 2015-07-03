package com.pb.dashboard.tester.middleware;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EASDataParser {

    private Pattern dbIPpattern = Pattern.compile("Server=\\d+\\.\\d+\\.\\d+\\.\\d+");
    private Pattern portPattern = Pattern.compile("port=\\d{2,4}");
    private Pattern dbNamePattern = Pattern.compile("DatabaseName=.+?:");
    private Pattern appNamePattern = Pattern.compile(".+\\.");

    private String noInfo = "Нет информации";

    public String parseDbIP(String s) {
        Matcher m = dbIPpattern.matcher(s);
        if (m.find()) return parseValue(s.substring(m.start(), m.end()));
        return noInfo;
    }

    public String parsePort(String s) {
        Matcher m = portPattern.matcher(s);
        if (m.find()) return parseValue(s.substring(m.start(), m.end()));
        return noInfo;
    }

    public String parseDbName(String s) {
        Matcher m = dbNamePattern.matcher(s);
        if (m.find()) return parseValue(s.substring(m.start(), m.end() - 1));
        return noInfo;
    }

    public String parseAppName(String s) {
        Matcher m = appNamePattern.matcher(s);
        if (m.find()) return s.substring(m.start(), m.end() - 1);
        return noInfo;
    }

    private String parseValue(String s) {
        String[] parts = s.split("=");
        return parts[1];
    }

}
