package com.pb.dashboard.dao.entity.biplanesupport;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by vlad
 * Date: 10.04.15_10:22
 */

@Entity
public class ErrorCount6Month implements ErrorCountI, Serializable {

    private static final long serialVersionUID = -5928565206331125607L;
    @Id
    @Column(name = "y")
    private int year;
    @Id
    @Column(name = "m")
    private int month;
    @Column(name = "err_business_cnt")
    private int business;
    @Column(name = "err_system_cnt")
    private int system;
    @Column(name = "err_499_cnt")
    private int err499Code;

    @Transient
    private LocalDate date = new LocalDate(DateTimeZone.UTC);
    @Transient
    private LocalDateTime dateTime;


    public ErrorCount6Month() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public LocalDateTime getDate() {
        if (dateTime == null) {
            dateTime = date.withYear(year).withMonthOfYear(month).toLocalDateTime(new LocalTime(0, 0));
        }
        return dateTime;
    }

    @Override
    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    @Override
    public int getSystem() {
        return system;
    }

    @Override
    public int get499Code() {
        return err499Code;
    }

    public void setSystem(int system) {
        this.system = system;
    }

    public int getErr499Code() {
        return err499Code;
    }

    public void setErr499Code(int err499Code) {
        this.err499Code = err499Code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, business, system, err499Code, date, dateTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ErrorCount6Month other = (ErrorCount6Month) obj;
        return Objects.equals(this.year, other.year) && Objects.equals(this.month, other.month) && Objects.equals(this.business, other.business) && Objects.equals(this.system, other.system) && Objects.equals(this.err499Code, other.err499Code) && Objects.equals(this.date, other.date) && Objects.equals(this.dateTime, other.dateTime);
    }
}
