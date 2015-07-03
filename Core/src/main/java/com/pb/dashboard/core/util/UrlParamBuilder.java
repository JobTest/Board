package com.pb.dashboard.core.util;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vlad
 * Date: 01.10.14
 */
public class UrlParamBuilder {

    private static final Logger log = Logger.getLogger(UrlParamBuilder.class);
    public static final String CHARACTER_ENCODING = "UTF-8";
    public static final String SEPARATOR_PARAMS = "&";
    public static final String SEPARATOR_PARAM = "=";
    public static final String BEGIN_PARAMS = "?";

    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final int TWO_PARAMS = 2;

    private StringBuilder sb = new StringBuilder(BEGIN_PARAMS);


    public static String getPath(Map<Object, Object> params) {
        UrlParamBuilder builderParams = new UrlParamBuilder();
        for (Map.Entry<Object, Object> param : params.entrySet()) {
            builderParams.addParam(param.getKey(), encodingValue(param.getValue()));
        }
        return builderParams.toString();
    }

    public static Map<String, String> parseParams(String urlParams) {
        Map<String, String> params = new HashMap<>();
        urlParams = deleteBeginParams(urlParams);

        String[] parameters = urlParams.split(SEPARATOR_PARAMS);

        for (String parameter : parameters) {
            String[] keyValue = parameter.split(SEPARATOR_PARAM, TWO_PARAMS);
            if (keyValue.length == TWO_PARAMS) {
                params.put(keyValue[KEY], decodeValue(keyValue[VALUE]));
            }
        }
        return params;
    }

    private static String deleteBeginParams(String urlParams) {
        int index = urlParams.indexOf(BEGIN_PARAMS);
        if (index == -1 || urlParams.isEmpty()) {
            return "";
        }
        return urlParams.substring(index + BEGIN_PARAMS.length(), urlParams.length());
    }

    private static Object encodingValue(Object value) {
        try {
            return URLEncoder.encode(String.valueOf(value), CHARACTER_ENCODING);
        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported encoding \"" + CHARACTER_ENCODING + "\"", e);
        }
        return value;
    }

    private static String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, CHARACTER_ENCODING);
        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported encoding \"" + CHARACTER_ENCODING + "\"", e);
        }
        return value;
    }

    public UrlParamBuilder addParam(Object key, Object value) {
        sb.append(key).append(SEPARATOR_PARAM).append(encodingValue(value)).append(SEPARATOR_PARAMS);
        return this;
    }

    @Override
    public String toString() {
        int countDelete = SEPARATOR_PARAMS.length();
        sb.delete(sb.length() - countDelete, sb.length());
        return sb.toString();
    }
}