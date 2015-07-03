package com.pb.dashboard.core.util;

import javax.annotation.Nullable;

/**
 * Created by vlad.
 * Date: 02.10.14
 */
public final class IntegerUtil {

    private IntegerUtil() {
    }

    public static boolean checkInt(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        String str = String.valueOf(obj);
        return str.matches("^-?\\d+$");
    }

    public static int toInt(@Nullable Boolean b) {
        return b == null ? 0 : b ? 1 : 0;
    }
}
