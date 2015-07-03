package com.pb.dashboard.monitoring.errors.all.navigator;

/**
 * Created by vlad
 * Date: 16.12.14_17:32
 */
public enum NavigatorItem {

    COUNTRY(0),
    COMPLEX(1),
    METRIC(2),
    INTERFACE(3),
    GROUP(4),
    RESPONSIBLE(5),
    CONSUMER(6);

    private final int pkey;

    NavigatorItem(int pkey) {
        this.pkey = pkey;
    }

    public int getPkey() {
        return pkey;
    }
}