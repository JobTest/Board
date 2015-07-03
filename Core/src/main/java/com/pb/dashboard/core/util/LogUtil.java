package com.pb.dashboard.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class LogUtil {

    private LogUtil() {
    }

    /**
     * Converts exception Stack Trace to String for convenient logging
     *
     * @param e Exception caught
     * @return Stack Trace converted to String
     */
    public static String stackTraceToString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

}
