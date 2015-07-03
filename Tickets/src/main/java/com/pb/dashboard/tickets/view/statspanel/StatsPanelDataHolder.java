package com.pb.dashboard.tickets.view.statspanel;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StatsPanelDataHolder {

    private Integer totalSales;
    private BigDecimal totalRevenue;
    private BigDecimal totalTurnover;

    private BigDecimal avgTicketCost;
    private BigDecimal avgRevenuePerTicket;

    public StatsPanelDataHolder(Object[] data) {
        setData(data);
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public BigDecimal getTotalTurnover() {
        return totalTurnover;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public BigDecimal getAvgTicketCost() {
        return avgTicketCost;
    }

    public BigDecimal getAvgRevenuePerTicket() {
        return avgRevenuePerTicket;
    }

    private void setData(Object[] data) {
        totalSales = Integer.parseInt(((String) data[1]).replaceAll(",", ""));
        totalRevenue = new BigDecimal(((String) data[2]).replaceAll(",", ""));
        totalTurnover = new BigDecimal(((String) data[3]).replaceAll(",", ""));
        BigDecimal sales = new BigDecimal(totalSales); // convert Integer to BigDecimal
        avgTicketCost = (isValidInput(totalTurnover, sales)) ?
                totalTurnover.divide(sales, 2, RoundingMode.HALF_UP) : new BigDecimal(0);
        avgRevenuePerTicket = (isValidInput(totalRevenue, sales)) ?
                totalRevenue.divide(sales, 2, RoundingMode.HALF_UP) : new BigDecimal(0);
    }

    private boolean isValidInput(BigDecimal first, BigDecimal second) {
        return first != null && first.compareTo(BigDecimal.ZERO) != 0 &&
               second != null && second.compareTo(BigDecimal.ZERO) != 0;
    }

}
