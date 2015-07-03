package com.pb.dashboard.external.charts.data;

public class DailyChartCollum {
    private int lines = 0;
    private String key;
    private int files = 0;

    public int getFiles() {
        return files;
    }

    public void setFiles(int files) {
        this.files += files;
    }

    public DailyChartCollum(String key) {
        this.key = key;
    }

    public void setLines(int value) {
        this.lines += value;
    }

    public int getLines() {
        return lines;
    }

    public String getKey() {
        return key;
    }
}
