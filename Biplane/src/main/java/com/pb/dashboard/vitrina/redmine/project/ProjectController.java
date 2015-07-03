package com.pb.dashboard.vitrina.redmine.project;

import com.pb.dashboard.vitrina.redmine.version.Version;
import com.pb.dashboard.vitrina.redmine.version.VersionController;

import java.io.Serializable;
import java.util.ArrayList;

public class ProjectController implements Serializable {
    private Project project;
    private ProjectView view;
    private ArrayList<VersionController> versionControllers;

    public ProjectController(Project project) {
        this.project = project;
        init();
    }

    private void init() {
        initVersionControllers();
        initView();
    }

    private void initView() {
        view = new ProjectView(project.getName(), project.getTaskOutVersion().size());
        for (int i = 0; i < versionControllers.size(); i++) {
            view.addView(versionControllers.get(i).getView());
        }
    }

    private void initVersionControllers() {
        ArrayList<Version> versions = project.getVersions();
        versionControllers = new ArrayList<VersionController>(versions.size());
        for (int i = 0; i < versions.size(); i++) {
            versionControllers.add(new VersionController(versions.get(i)));
        }
    }

    public ProjectView getView() {
        return view;
    }

    public ArrayList<VersionController> getVersionControllers() {
        return versionControllers;
    }
}