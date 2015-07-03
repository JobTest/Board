package com.pb.dashboard.external.debtload.view.load;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class TotalsStringsPanel extends HorizontalLayout {

private Label[] labels = new Label[3];

    public TotalsStringsPanel() {
        setSpacing(true);
        for (int i = 0; i < labels.length; i++) {
            if (i == 0) addComponent(new Label("Всего загрузок строк: "));
            if (i == 1) addComponent(new Label("Загрузок строк с расхождениями: "));
            if (i == 2) addComponent(new Label("Успешные загрузки строк: "));
            labels[i] = new Label();
            labels[i].addStyleName("total-label");
            addComponent(labels[i]);
        }
    }

    public void setData (int loadsCntStr, int diffLoadsCntStr, double errLoadsCntStr) {
        labels[0].setValue(loadsCntStr + "");
        labels[1].setValue(diffLoadsCntStr + "");
        labels[2].setValue(errLoadsCntStr + "%");
    }
}
