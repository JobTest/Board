package com.pb.dashboard.dao.entity.vitrinametrics;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by vlad
 * Date: 24.03.15_13:51
 */

@Entity
public class SlaTimingHour implements SlaTimingI, Serializable {

    private static final long serialVersionUID = 6003558058445843008L;
    @Id
    @Column(name = "pkey_interface_sla")
    private int pkeySlaInterface;
    @Id
    @Column(name = "date")
    private int date;
    @Id
    @Column(name = "hour")
    private int hour;
    @Column(name = "timing_avg")
    private int timingAvg;
    @Column(name = "timing_90")
    private int timing90;
    @Column(name = "timing_95")
    private int timing95;
    @Column(name = "timing_99")
    private int timing99;

    @Transient
    private DateTime dateTime;

    @Override
    public DateTime getDateTime() {
        if (dateTime == null) {
            String str = String.valueOf(date);
            dateTime = DateTime.parse(str, DateTimeFormat.forPattern("yyyyMMdd"));
            dateTime = dateTime.withTime(hour, 0, 0, 0);
        }
        return dateTime;
    }

    @Override
    public int getTimeTiming(Type type) {
        switch (type) {
            case AVG:
                return timingAvg;
            case T90:
                return timing90;
            case T95:
                return timing95;
            case T99:
                return timing99;
        }
        throw new IllegalArgumentException("TimingsType[" + type + "] is not exists.");
    }

    public int getPkeySlaInterface() {
        return pkeySlaInterface;
    }

    public void setPkeySlaInterface(int pkeySlaInterface) {
        this.pkeySlaInterface = pkeySlaInterface;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getTimingAvg() {
        return timingAvg;
    }

    public void setTimingAvg(int timingAvg) {
        this.timingAvg = timingAvg;
    }

    public int getTiming90() {
        return timing90;
    }

    public void setTiming90(int timing90) {
        this.timing90 = timing90;
    }

    public int getTiming95() {
        return timing95;
    }

    public void setTiming95(int timing95) {
        this.timing95 = timing95;
    }

    public int getTiming99() {
        return timing99;
    }

    public void setTiming99(int timing99) {
        this.timing99 = timing99;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkeySlaInterface, date, hour, timingAvg, timing90, timing95, timing99, dateTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SlaTimingHour other = (SlaTimingHour) obj;
        return Objects.equals(this.pkeySlaInterface, other.pkeySlaInterface) && Objects.equals(this.date, other.date) && Objects.equals(this.hour, other.hour) && Objects.equals(this.timingAvg, other.timingAvg) && Objects.equals(this.timing90, other.timing90) && Objects.equals(this.timing95, other.timing95) && Objects.equals(this.timing99, other.timing99) && Objects.equals(this.dateTime, other.dateTime);
    }
}