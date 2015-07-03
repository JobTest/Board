package com.pb.dashboard.external.debtload.view.load;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class TotalsPanel extends HorizontalLayout {

    private Label[] labels = new Label[4];

    public TotalsPanel() {
        setSpacing(true);
        for (int i = 0; i < labels.length; i++) {
            if (i == 0) addComponent(new Label("Всего загрузок: "));
            if (i == 1) addComponent(new Label("Загрузок с расхождениями: "));
            if (i == 2) addComponent(new Label("Загрузок с ошибками: "));
            if (i == 3) addComponent(new Label("Успешные загрузки: "));
            labels[i] = new Label();
            labels[i].addStyleName("total-label");
            addComponent(labels[i]);
        }
    }

    public void setData(int loadsCnt, int diffLoadsCnt, int errLoadsCnt, double percentLoadsCnt) {
        labels[0].setValue(loadsCnt + "");
        labels[1].setValue(diffLoadsCnt + "");
        labels[2].setValue(errLoadsCnt + "");
        labels[3].setValue(percentLoadsCnt + "%");
    }

}
