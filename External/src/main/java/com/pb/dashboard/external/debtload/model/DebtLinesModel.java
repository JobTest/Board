package com.pb.dashboard.external.debtload.model;

public class DebtLinesModel {
    private String date;
    private String lines;

    public DebtLinesModel(String date, String lines) {
        this.date = date;
        this.lines = lines;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLines() {
        return lines;
    }

}
