package com.pb.dashboard.monitoring.timings.navigator;

/**
 * Created by vlad
 * Date: 16.12.14_17:32
 */
public enum NavigatorItem {

    COUNTRY(0),
    COMPLEX(1),
    INTERFACE(2),
    METRIC(3);
    private final int pkey;

    NavigatorItem(int pkey) {
        this.pkey = pkey;
    }

    public int getPkey() {
        return pkey;
    }
}