package com.pb.dashboard.vitrina.statistics.chart;

public class ChartDataHolder {

    private Integer payments = 0;
    private Integer cash = 0;
    private Integer noCash = 0;
    private Integer phys = 0;
    private Integer jur = 0;
    private Integer debt = 0;

    public Integer getPayments() {
        return payments;
    }

    public void addPayments(Integer payments) {
        this.payments += payments;
    }

    public Integer getCash() {
        return cash;
    }

    public void addCash(Integer cash) {
        this.cash += cash;
    }

    public Integer getNoCash() {
        return noCash;
    }

    public void addNoCash(Integer noCash) {
        this.noCash += noCash;
    }

    public Integer getPhys() {
        return phys;
    }

    public void addPhys(Integer phys) {
        this.phys += phys;
    }

    public Integer getJur() {
        return jur;
    }

    public void addJur(Integer jur) {
        this.jur += jur;
    }

    public Integer getDebt() {
        return debt;
    }

    public void addDebt(Integer debt) {
        this.debt += debt;
    }
}
