package com.pb.dashboard.monitoring.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by vlad
 * Date: 28.04.15_17:46
 */
public final class ContextFactory {

    public static final String RELATIVE_PATH_CONTEXT = "/timings-context.xml";
    private static ApplicationContext context = new ClassPathXmlApplicationContext(RELATIVE_PATH_CONTEXT);

    private ContextFactory() {
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        ContextFactory.context = context;
    }

}