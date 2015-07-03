package com.pb.dashboard.monitoring.timings.chooser;

/**
 * Created by vlad
 * Date: 20.11.14_10:33
 */
public enum ChooserItem {

    LOGARITHMIC("Логарифмическая шкала"),
    ARITHMETIC("Арифметическая шкала");

    private String name;

    private ChooserItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
