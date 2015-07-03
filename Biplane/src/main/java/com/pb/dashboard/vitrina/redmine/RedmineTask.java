package com.pb.dashboard.vitrina.redmine;

import com.vaadin.ui.Link;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RedmineTask {

    private String id;
    private Link url;
    private String subject;
    private String type;
    private Calendar calendar;
    private String status;

    public RedmineTask() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Link getUrl() {
        return url;
    }

    public void setUrl(Link url) {
        this.url = url;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setData(String stringDate) {
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date date = sdf.parse(stringDate);
            calendar.setTime(date);
        } catch (ParseException e) {
            calendar.add(Calendar.YEAR, 1);
        }
        this.calendar = calendar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RedmineTask{" +
                "id='" + id + '\'' +
                ", url=" + url +
                ", subject='" + subject + '\'' +
                ", type='" + type + '\'' +
                ", calendar=" + calendar +
                ", status='" + status + '\'' +
                '}';
    }
}