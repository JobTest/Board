package com.pb.dashboard.external.debtload.model;

public class DebtLoadModel extends AbstractDebtModel {

    private String date;
    private String loadStartTime;
    private String loadEndTime;
    private int linesLoaded;
    private int diff;
    private String type;
    private String diffLink = "";
    private String errorText;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLoadStartTime() {
        return loadStartTime;
    }

    public void setLoadStartTime(String loadStartTime) {
        this.loadStartTime = loadStartTime;
    }

    public String getLoadEndTime() {
        return loadEndTime;
    }

    public void setLoadEndTime(String loadEndTime) {
        this.loadEndTime = loadEndTime;
    }

    public int getLinesLoaded() {
        return linesLoaded;
    }

    public void setLinesLoaded(int linesLoaded) {
        this.linesLoaded = linesLoaded;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiffLink() {
        return diffLink;
    }

    public void setDiffLink(String diffLink) {
        this.diffLink = diffLink;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DebtLoadModel that = (DebtLoadModel) o;

        if (linesLoaded != that.linesLoaded) return false;
        if (recipientId != that.recipientId) return false;
        if (bank != null ? !bank.equals(that.bank) : that.bank != null) return false;
        if (loadEndTime != null ? !loadEndTime.equals(that.loadEndTime) : that.loadEndTime != null) return false;
        if (loadStartTime != null ? !loadStartTime.equals(that.loadStartTime) : that.loadStartTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 3;
        result = 31 * result + recipientId;
        result = 31 * result + (bank != null ? bank.hashCode() : 0);
        result = 31 * result + (loadStartTime != null ? loadStartTime.hashCode() : 0);
        result = 31 * result + (loadEndTime != null ? loadEndTime.hashCode() : 0);
        result = 31 * result + linesLoaded;

        return result;
    }

    @Override
    public String toString() {
        return String.format("Recipient ID: %d%n Date: %s, Recipient name: %s, Country: %s%n Load start time: %s, Load end time: %s, " +
                "Lines loaded: %d, Differences: %d, Type: %s, Diff link: %s, Error text: %s, Branch: %s%n",
                recipientId, date, recipientName, bank, loadStartTime, loadEndTime, linesLoaded, diff, type, diffLink,
                errorText, branch);
    }

}
