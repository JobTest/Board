package com.pb.dashboard.vitrina.redmine.version;

import com.pb.dashboard.vitrina.redmine.RedmineTask;

import java.util.ArrayList;
import java.util.Calendar;

public class Version {

    public enum Status {
        OPENED("Открыта"),
        CLOSED("Закрыта"),
        DELETED("Удалена");

        private String name;

        private Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Status getStatus(String name) {
            for (Status type : Status.values()) {
                if (type.getName().equals(name)) {
                    return type;
                }
            }
            throw new RuntimeException("unknown status");
        }
    }

    public enum Tracker {
        ERROR("Ошибка"),
        OTHER("Остальные");

        private String name;

        private Tracker(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Tracker getTracker(String name) {
            for (Tracker tracker : Tracker.values()) {
                if (tracker.getName().equals(name)) {
                    return tracker;
                }
            }
            throw new RuntimeException("unknown tracker");
        }
    }

    private String name;
    private ArrayList<RedmineTask> tasks;

    public Version() {
    }

    public Version(String name) {
        this.name = name;
    }

    public Version(String name, ArrayList<RedmineTask> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<RedmineTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<RedmineTask> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<RedmineTask> getTasks(Status status) {
        ArrayList<RedmineTask> res = new ArrayList<RedmineTask>();
        boolean close = status == Status.CLOSED;

        for (int i = 0; i < tasks.size(); i++) {
            if (!close ^ Status.CLOSED.getName().equalsIgnoreCase(tasks.get(i).getStatus())) {
                res.add(tasks.get(i));
            }
        }
        return res;
    }

    public ArrayList<RedmineTask> getTasks(Status status, Tracker tracker) {
        ArrayList<RedmineTask> res = new ArrayList<RedmineTask>();
        boolean close = status == Status.CLOSED;
        boolean error = tracker == Tracker.ERROR;

        for (int i = 0; i < tasks.size(); i++) {
            if (!close ^ Status.CLOSED.getName().equalsIgnoreCase(tasks.get(i).getStatus())) {
                if (!error ^ Tracker.ERROR.getName().equalsIgnoreCase(tasks.get(i).getType())) {
                    res.add(tasks.get(i));
                }
            }
        }
        return res;
    }

    public ArrayList<RedmineTask> getPastTasks() {
        ArrayList<RedmineTask> res = new ArrayList<RedmineTask>();
        for (int i = 0; i < tasks.size(); i++) {
            RedmineTask task = tasks.get(i);
            if (!Status.CLOSED.getName().equalsIgnoreCase(task.getStatus()) && !"Отклонена".equalsIgnoreCase(task.getStatus()))
                if (task.getCalendar().before(Calendar.getInstance())) {
                    res.add(task);
                }
        }
        return res;
    }
}