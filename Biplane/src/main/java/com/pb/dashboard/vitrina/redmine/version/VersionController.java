package com.pb.dashboard.vitrina.redmine.version;

import com.pb.dashboard.vitrina.redmine.RedmineChart;

public class VersionController {

    private VersionView view;
    private Version version;
    private RedmineChart redmineChart;

    public VersionController(Version version) {
        this.version = version;
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        view = new VersionView();

        redmineChart = new RedmineChart(version);
        view.setChart(redmineChart.getChart());
        view.init();

        view.getClickLabel().setLabel(String.valueOf(version.getPastTasks().size()));
    }

    public VersionView getView() {
        return view;
    }

    public Version getVersion() {
        return version;
    }

    public RedmineChart getRedmineChart() {
        return redmineChart;
    }
}