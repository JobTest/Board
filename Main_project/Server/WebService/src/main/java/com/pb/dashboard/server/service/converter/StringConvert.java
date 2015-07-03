package com.pb.dashboard.server.service.converter;

/**
 * Created by vlad
 * Date: 06.05.15_17:01
 */
public class StringConvert {

    public static String convert(String str) {
        if (str == null || str.equals("null")) {
            return null;
        }
        return str;
    }
}
