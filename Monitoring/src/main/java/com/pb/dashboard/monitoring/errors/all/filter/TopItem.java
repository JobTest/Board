package com.pb.dashboard.monitoring.errors.all.filter;

/**
 * Created by vlad
 * Date: 06.11.14_11:06
 */
public enum TopItem {

    T10(10),
    T20(20);

    private int count;

    private TopItem(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return String.valueOf(count);
    }
}
