package com.pb.dashboard.core.util;

/**
 * Created by vlad
 * Date: 22.10.14_8:54
 */
public final class StringUtil {

    public static final String EMPTY = "";

    private StringUtil() {
    }

    public static boolean isEmptyOrNull(Object obj) {
        return obj == null || String.valueOf(obj).trim().isEmpty();
    }
}
