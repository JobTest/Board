package com.pb.dashboard.vitrina.trends.charts;

public enum ChartEnum {
    PAYDESK_HOUR_CASH(3),
    PAYDESK_DAY_CASH(3),
    PAYDESK_HOUR_PHYS(2),
    PAYDESK_DAY_PHYS(2),

    TERMINAL_HOUR_CASH(3),
    TERMINAL_DAY_CASH(3),
    TERMINAL_HOUR_PHYS(2),
    TERMINAL_DAY_PHYS(2),

    P24_HOUR_CASH(3),
    P24_DAY_CASH(3),
    P24_HOUR_PHYS(2),
    P24_DAY_PHYS(2),

    L3700_HOUR_CASH(3),
    L3700_DAY_CASH(3),
    L3700_HOUR_PHYS(2),
    L3700_DAY_PHYS(2);

    private int seriesCount;

    private ChartEnum(int seriesCount) {
        this.seriesCount = seriesCount;
    }

    public int getSeriesCount() {
        return seriesCount;
    }
}
