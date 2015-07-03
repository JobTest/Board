package com.pb.dashboard.vitrina.timeline.view;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SubPanel extends VerticalLayout {

    private Label minHead = new Label("мин:");
    private Label maxHead = new Label("макс:");
    private Label avgHead = new Label("средн:");

    private Label min = new Label();
    private Label max = new Label();
    private Label avg = new Label();

    public SubPanel(String caption) {
        setCaption(caption);
        setStyleName("sub-panel");

        min.addStyleName("right");
        min.addStyleName("min");
        max.addStyleName("right");
        max.addStyleName("max");
        avg.addStyleName("right");
        avg.addStyleName("avg");

        addComponent(getLine(minHead, min));
        addComponent(getLine(maxHead, max));
        addComponent(getLine(avgHead, avg));
    }

    public void setValues(int[] values) {
        min.setValue(String.format("%,d", values[0]));
        max.setValue(String.format("%,d", values[1]));
        avg.setValue(String.format("%,d", values[2]));
    }

    private HorizontalLayout getLine(Label first, Label second) {
        HorizontalLayout line = new HorizontalLayout();
        line.setStyleName("line");
        line.setSpacing(true);
        line.addComponent(first);
        line.addComponent(second);
        line.setComponentAlignment(second, Alignment.TOP_RIGHT);
        return line;
    }

}
