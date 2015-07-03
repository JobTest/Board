package com.pb.dashboard.itdashbord.errors.errorSession;

public class ErrorsSessionFiltersListener {
    private String serchString = "NaN";

    private String dateFrom = "";
    private String dateTo = "";

    private String type="NaN";

    public ErrorsSessionFiltersListener() {
    }

    public String getSerchString() {
        return serchString;
    }

    public void setSerchString(String serchString) {
        this.serchString = serchString;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
