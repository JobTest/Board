package com.pb.dashboard.monitoring.errors.all.db.container;

public class DescriptionRecipient {

    private String company = "-";
    private String filial = "-";

    public DescriptionRecipient() {
    }

    public DescriptionRecipient(String company, String filial) {
        this.company = company;
        this.filial = filial;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    @Override
    public String toString() {
        return "DescriptionCompanyData{" +
                "company='" + company + '\'' +
                ", filial='" + filial + '\'' +
                "} " + super.toString();
    }
}
