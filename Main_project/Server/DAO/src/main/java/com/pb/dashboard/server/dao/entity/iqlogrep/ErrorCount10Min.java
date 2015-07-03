package com.pb.dashboard.server.dao.entity.iqlogrep;

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
 * Date: 10.04.15_10:21
 */
@Entity
public class ErrorCount10Min implements ErrorCountI, Serializable {

    private static final long serialVersionUID = 3735081622821822615L;
    @Id
    @Column(name = "h")
    private int hour;
    @Id
    @Column(name = "m10")
    private int minute;
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

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public LocalDateTime getDate() {
        if (dateTime == null) {
            dateTime = date.toLocalDateTime(new LocalTime(hour, minute));
        }
        return dateTime;
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute, business, system, err499Code);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ErrorCount10Min other = (ErrorCount10Min) obj;
        return Objects.equals(this.hour, other.hour) && Objects.equals(this.minute, other.minute) && Objects.equals(this.business, other.business) && Objects.equals(this.system, other.system) && Objects.equals(this.err499Code, other.err499Code);
    }
}
