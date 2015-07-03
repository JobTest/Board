package com.pb.dashboard.tickets.view.statspanel;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class StatsPanel extends VerticalLayout {

    private Label sales;
    private Label turnover;
    private Label revenue;
    private Label avgTicketCost;
    private Label avgRevenuePerTicket;

    public StatsPanel() {
        setHeight("480");
        setMargin(true);
        addStyleName("layout-panel");
        addComponents(getTitleLabel("Всего продано билетов"), sales = new Label(), new Label("<hr>", ContentMode.HTML));
        addComponents(getTitleLabel("Общий оборот средств"), turnover = new Label(), new Label("<hr>", ContentMode.HTML));
        addComponents(getTitleLabel("Полученный доход"), revenue = new Label(), new Label("<hr>", ContentMode.HTML));
        addComponents(getTitleLabel("Средняя стоимость одного билета"), avgTicketCost = new Label(),
                new Label("<hr>", ContentMode.HTML));
        addComponents(getTitleLabel("Средний доход с одного билета"), avgRevenuePerTicket = new Label());

        Label[] numbers = { sales, turnover, revenue, avgTicketCost, avgRevenuePerTicket };
        for (Label number : numbers) {
            number.setStyleName("numbers");
        }
    }

    private Label getTitleLabel(String title) {
        Label label = new Label(title);
        label.setStyleName("number-labels");
        return label;
    }

    public void setData(StatsPanelDataHolder dataHolder) {
        String intFormat = "%,d";
        String doubleFormat = "%,.2f";
        sales.setValue(String.format(intFormat, dataHolder.getTotalSales()));
        turnover.setValue(String.format(doubleFormat, dataHolder.getTotalTurnover()));
        revenue.setValue(String.format(doubleFormat, dataHolder.getTotalRevenue()));
        avgTicketCost.setValue(String.format(doubleFormat, dataHolder.getAvgTicketCost()));
        avgRevenuePerTicket.setValue(String.format(doubleFormat, dataHolder.getAvgRevenuePerTicket()));
    }

}
