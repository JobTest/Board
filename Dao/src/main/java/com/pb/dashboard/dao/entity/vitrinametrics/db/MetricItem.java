package com.pb.dashboard.dao.entity.vitrinametrics.db;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.util.Objects;

/**
 * Created by vlad
 * Date: 10.12.14_14:21
 */
public class MetricItem {

    private LocalDateTime dateTime;
    private int min;
    private int max;
    private int avg;
    private int t90;
    private int t95;
    private int t99;
    private int count;
    private int errorCount;

    public MetricItem() {
        dateTime = DateTime.now().toLocalDateTime();
    }

    public MetricItem(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public MetricItem(LocalDateTime dateTime, int min, int max, int avg, int t90, int t95, int t99, int count, int errorCount) {
        this.dateTime = dateTime;
        this.min = min;
        this.max = max;
        this.avg = avg;
        this.t90 = t90;
        this.t95 = t95;
        this.t99 = t99;
        this.count = count;
        this.errorCount = errorCount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime calendar) {
        this.dateTime = calendar;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getAvg() {
        return avg;
    }

    public void setAvg(int avg) {
        this.avg = avg;
    }

    public int getT90() {
        return t90;
    }

    public void setT90(int t90) {
        this.t90 = t90;
    }

    public int getT95() {
        return t95;
    }

    public void setT95(int t95) {
        this.t95 = t95;
    }

    public int getT99() {
        return t99;
    }

    public void setT99(int t99) {
        this.t99 = t99;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, min, max, avg, t90, t95, t99, count, errorCount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final MetricItem other = (MetricItem) obj;
        return Objects.equals(this.dateTime, other.dateTime)
                && Objects.equals(this.min, other.min)
                && Objects.equals(this.max, other.max)
                && Objects.equals(this.avg, other.avg)
                && Objects.equals(this.t90, other.t90)
                && Objects.equals(this.t95, other.t95)
                && Objects.equals(this.t99, other.t99)
                && Objects.equals(this.count, other.count)
                && Objects.equals(this.errorCount, other.errorCount);
    }
}
