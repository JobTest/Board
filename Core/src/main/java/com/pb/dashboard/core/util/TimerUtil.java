package com.pb.dashboard.core.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public final class TimerUtil {

    private static long timeS = System.nanoTime();

    private TimerUtil() {
    }

    public static void start(Logger log) {
        if (log.getParent().getLevel() == Level.TRACE) {
            timeS = System.nanoTime();
        }
    }

    public static void finish(Logger log) {
        if (log.getParent().getLevel() == Level.TRACE) {
            long ms = ((System.nanoTime() - timeS) / (1000 * 1000));
            log.trace("Time: " + ((double) ms / 1000) + " сек. (" + ms + ")");
        }
    }
}