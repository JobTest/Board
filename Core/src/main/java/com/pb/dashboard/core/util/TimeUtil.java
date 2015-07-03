package com.pb.dashboard.core.util;

/**
 * Created by vlad
 * Date: 25.12.14_14:23
 */
public final class TimeUtil {

    public static final int NANO_IN_MILLIS = 1000000;

    private TimeUtil() {
    }

    public static long nanoToMillis(long nano) {
        return nano / NANO_IN_MILLIS;
    }
}
