package com.pb.dashboard.server.dao.entity.iqlogrep;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by vlad
 * Date: 02.02.15_9:33
 */

@Entity
public class CountForCompany {

    @Id
    @Column(name = "h")
    private int hour;
    @Column(name = "cnt")
    private int count;
    @Column(name = "err_cnt")
    private int errorCount;
    @Column(name = "duration_max")
    private int durationMax;
    @Column(name = "duration_90")
    private int duration90;
    @Column(name = "duration_95")
    private int duration95;
    @Column(name = "duration_99")
    private int duration99;

    public int getHour() {
        return hour;
    }

    public int getCount() {
        return count;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public int getDurationMax() {
        return durationMax;
    }

    public int getDuration90() {
        return duration90;
    }

    public int getDuration95() {
        return duration95;
    }

    public int getDuration99() {
        return duration99;
    }
}
