package com.pb.dashboard.server.dao.entity.iqlogrep;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by vlad
 * Date: 27.01.15_15:00
 */
@Entity
public class RecipientEntity {

    @Id
    @Column(name = "company_id")
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getDurationMax() {
        return durationMax;
    }

    public void setDurationMax(int durationMax) {
        this.durationMax = durationMax;
    }

    public int getDuration90() {
        return duration90;
    }

    public void setDuration90(int duration90) {
        this.duration90 = duration90;
    }

    public int getDuration95() {
        return duration95;
    }

    public void setDuration95(int duration95) {
        this.duration95 = duration95;
    }

    public int getDuration99() {
        return duration99;
    }

    public void setDuration99(int duration99) {
        this.duration99 = duration99;
    }

}
