package com.pb.dashboard.itdashbord.errors;

public class ErrorsFiltersListener {
    private String searchString = "NaN";

    private String dateFrom = "";
    private String dateTo = "";

    private String baranch = "NaN";
    private String group = "NaN";
    private String stypeDebt = "NaN";

    public ErrorsFiltersListener() {
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getBaranch() {
        return baranch;
    }

    public void setBaranch(String baranch) {
        this.baranch = baranch;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getStypeDebt() {
        return stypeDebt;
    }

    public void setStypeDebt(String stypeDebt) {
        this.stypeDebt = stypeDebt;
    }
}
