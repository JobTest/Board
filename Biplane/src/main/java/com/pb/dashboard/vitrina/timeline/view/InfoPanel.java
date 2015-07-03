package com.pb.dashboard.vitrina.timeline.view;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class InfoPanel extends HorizontalLayout {

    private VerticalLayout channels1;
    private VerticalLayout channels2;
    private VerticalLayout cashNoCash;
    private VerticalLayout physJur;
    private VerticalLayout debt;

    private SubPanel paydesk;
    private SubPanel terminal;

    private SubPanel p24;
    private SubPanel l3700;

    private SubPanel cash;
    private SubPanel noCash;

    private SubPanel phys;
    private SubPanel jur;

    private SubPanel debtSubPanel;

    public InfoPanel() {
        addStyleName("info-panel");
        setWidth("100%");
        setSpacing(true);

        channels1 = createPanel();

        paydesk = new SubPanel("Касса");
        terminal = new SubPanel("ТСО");

        channels2 = createPanel();

        p24 = new SubPanel("Приват24");
        l3700 = new SubPanel("3700");

        channels1.addComponent(buildSubPanelsColumn(paydesk, terminal));
        channels2.addComponent(buildSubPanelsColumn(p24, l3700));

        cashNoCash = createPanel();

        cash = new SubPanel("Наличные");
        noCash = new SubPanel("Безналичные");

        cashNoCash.addComponent(buildSubPanelsColumn(cash, noCash));

        physJur = createPanel();

        phys = new SubPanel("Физ.лица");
        jur = new SubPanel("Юр.лица");

        physJur.addComponent(buildSubPanelsColumn(phys, jur));
        debt = createPanel();

        debtSubPanel = new SubPanel("Задолженность");

        debt.addComponent(debtSubPanel);

        addComponent(wrapPanel(channels1));
        addComponent(wrapPanel(channels2));
        addComponent(wrapPanel(cashNoCash));
        addComponent(wrapPanel(physJur));
        addComponent(wrapPanel(debt));
    }

    private CssLayout wrapPanel(Component content) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();
        panel.addComponent(content);
        return panel;
    }

    private VerticalLayout createPanel() {
        VerticalLayout panel = new VerticalLayout();
        panel.setSizeFull();
        panel.setMargin(true);
        panel.setHeight("220px");
        return panel;
    }

    private VerticalLayout buildSubPanelsColumn(SubPanel first, SubPanel second) {
        VerticalLayout column = new VerticalLayout();
        column.setSpacing(true);
        column.addComponent(first);
        second.addStyleName("bottom-sub-panel");
        column.addComponent(second);
        return column;
    }

    public void setData(List<int[]> numbers) {
        paydesk.setValues(numbers.get(0));
        terminal.setValues(numbers.get(1));
        p24.setValues(numbers.get(2));
        l3700.setValues(numbers.get(3));

        cash.setValues(numbers.get(4));
        noCash.setValues(numbers.get(5));

        phys.setValues(numbers.get(6));
        jur.setValues(numbers.get(7));
        debtSubPanel.setValues(numbers.get(8));
    }

}
