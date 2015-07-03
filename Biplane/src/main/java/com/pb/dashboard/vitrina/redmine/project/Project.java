package com.pb.dashboard.vitrina.redmine.project;

import com.pb.dashboard.vitrina.redmine.RedmineTask;
import com.pb.dashboard.vitrina.redmine.version.Version;

import java.util.ArrayList;

public class Project {
    private int id;
    private String name;
    private int parentId;
    private String target;
    private ArrayList<Version> versions;
    private ArrayList<RedmineTask> taskOutVersion;

    public Project() {
        init();
    }

    private void init() {
        versions = new ArrayList<Version>(3);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public ArrayList<Version> getVersions() {
        return versions;
    }

    public void addVersion(Version version) {
        versions.add(version);
    }

    public ArrayList<RedmineTask> getTaskOutVersion() {
        return taskOutVersion;
    }

    public void setTaskOutVersion(ArrayList<RedmineTask> taskOutVersion) {
        this.taskOutVersion = taskOutVersion;
    }

    public ArrayList<RedmineTask> getAllTask(){
        ArrayList<RedmineTask> res = new ArrayList<RedmineTask>();
        for (Version version : versions) {
            res.addAll(version.getTasks());
        }
        return res;
    }
}
