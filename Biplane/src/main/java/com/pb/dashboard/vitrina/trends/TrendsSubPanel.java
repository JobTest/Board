package com.pb.dashboard.vitrina.trends;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class TrendsSubPanel extends VerticalLayout {

    private Label total = new Label();
    private Label increase = new Label();
    private Label percent = new Label();

    public TrendsSubPanel(boolean isDayPanel) {
        Label totalLabel = new Label("Всего:");
        Label previousLabel = new Label("Прирост:");
        Label percentLabel = new Label("В процентах:");
        addStyleName("sub-panel");
        addComponent(totalLabel);
        addComponent(center(total));
        total.setDescription("Количество платежей");
        addComponent(left(previousLabel));
        addComponent(center(increase));
        increase.addStyleName("smaller");
        addComponent(left(percentLabel));
        addComponent(center(percent));
        percent.addStyleName("smaller");
        String incrDesc = null,
               incrPercDesc = null;
        if (isDayPanel) {
            incrDesc = String.format("Прирост в сравнении с предыдущим днем%n по количесву платежей");
            incrPercDesc = "Прирост в сравнении с предыдущим днем в процентах";
        } else {
            incrDesc = "Прирост в сравнении с предыдущим часом по количесву платежей";
            incrPercDesc = "Прирост в сравнении с предыдущим часом в процентах";
        }
        increase.setDescription(incrDesc);
        percent.setDescription(incrPercDesc);
    }

    public void setValues(Integer[] values) {
        String sign = "";
        total.setValue(String.format("%,d", values[0]));
        int diff = values[0] - values[1];
        if (diff < 0) {
            increase.removeStyleName("positive");
            percent.removeStyleName("positive");
            increase.addStyleName("negative");
            percent.addStyleName("negative");
            sign = "-";
            increase.setValue(String.format("%,d", diff));
        } else if (diff > 0) {
            increase.removeStyleName("negative");
            percent.removeStyleName("negative");
            increase.addStyleName("positive");
            percent.addStyleName("positive");
            sign = "+";
            increase.setValue(String.format("%s%,d", sign, diff));
        } else {
            increase.removeStyleName("negative");
            increase.removeStyleName("positive");
            percent.removeStyleName("negative");
            percent.removeStyleName("positive");
            increase.setValue("0");
        }
        double divisor = (values[0] == values[1]) ? 0 : (values[0] > values[1]) ? values[0] : values[1];
        double percentage = 0;
        if (divisor != 0) percentage = (Math.abs(diff) / divisor) * 100;
        percent.setValue(String.format("%s%.2f%%", sign, percentage));
    }

    private Label center(Label label) {
        label.addStyleName("center");
        return label;
    }

    private Label left(Label label) {
        label.addStyleName("left");
        return label;
    }

}
