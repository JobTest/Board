package com.pb.dashboard.dao.entity.vitrinametrics;

import com.pb.dashboard.core.util.IntegerUtil;
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
 * Date: 24.03.15_13:52
 */

@Entity
public class SlaCountDay implements SlaCountI, Serializable {

    private static final long serialVersionUID = -7964375578757134534L;
    @Id
    @Column(name = "pkey_interface_sla")
    private int pkeySlaInterface;
    @Id
    @Column(name = "date")
    private int date;
    @Column(name = "cnt")
    private int count;
    @Column(name = "err_cnt")
    private String errorCount;
    @Column(name = "percent")
    private double percent;
    @Transient
    private DateTime dateTime;

    @Override
    public int getPkeySlaInterface() {
        return pkeySlaInterface;
    }

    public int getDate() {
        return date;
    }

    @Override
    public DateTime getDateTime() {
        if (dateTime == null) {
            String str = String.valueOf(date);
            dateTime = DateTime.parse(str, DateTimeFormat.forPattern("yyyyMMdd"));
        }
        return dateTime;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int getErrorCount() {
        if (IntegerUtil.checkInt(errorCount)) {
            return Integer.parseInt(errorCount);
        }
        return 0;
    }

    public double getPercent() {
        return percent;
    }

    public void setPkeySlaInterface(int pkeySlaInterface) {
        this.pkeySlaInterface = pkeySlaInterface;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setErrorCount(int countError) {
        this.errorCount = String.valueOf(countError);
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkeySlaInterface, date, count, errorCount, percent, dateTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SlaCountDay other = (SlaCountDay) obj;
        return Objects.equals(this.pkeySlaInterface, other.pkeySlaInterface) && Objects.equals(this.date, other.date) && Objects.equals(this.count, other.count) && Objects.equals(this.errorCount, other.errorCount) && Objects.equals(this.percent, other.percent) && Objects.equals(this.dateTime, other.dateTime);
    }
}
