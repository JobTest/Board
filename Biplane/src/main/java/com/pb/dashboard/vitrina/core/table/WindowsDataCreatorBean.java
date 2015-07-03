package com.pb.dashboard.vitrina.core.table;

import com.pb.dashboard.vitrina.core.config.ConfigManager;
import com.pb.dashboard.vitrina.payment.data.StatEnum;
import com.pb.dashboard.vitrina.statistics.byday.StatsDayBean;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.util.List;

public class WindowsDataCreatorBean {

    private static final String[] columns = {"Период", "Прием", "Наличные", "Безналичные", "Задолженость", "Физ. лица", "Юр. лица"};
    private static TableBuilder builder = new TableBuilder();
    private static Table table;
    private static StatsDayBean bean = new StatsDayBean();
    private static ConfigManager manager;


    public static Window createWindow(StatEnum statEnum, ConfigManager newManager) {
        manager = newManager;
        Window window = getWindow();
        VerticalLayout row = new VerticalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(false);
        initTable(statEnum);
        row.addComponent(createPanels(table));
        window.setContent(row);
        window.setCaption(createCaption(statEnum));
        return window;
    }

    private static void initTable(StatEnum stat) {
        table = builder.buildTableproperty(false, columns, "");
        bean.setConfigManager(manager);
        bean.updateData(stat);
        fillTable(bean.getSumMetrics(), bean.getPayment(stat), table);
        table.setCaption("Всего: " + bean.getSum());
        table.setFooterVisible(false);
        table.setPageLength(0);
    }

    private static void fillTable(Object[] sumMetrics, List<Object[]> payments, Table table)
            throws UnsupportedOperationException {
        table.removeAllItems();
        table.setColumnFooter("Период", "Всего:");
        for (int i = 0; i < payments.size(); i++) {
            table.addItem(payments.get(i), new Integer(i + 1) + "");
        }
        for (int i = 1; i < columns.length; i++) {
            table.setColumnFooter(columns[i], (String) sumMetrics[i]);
        }
    }

    private static CssLayout createPanels(Table component) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
//        panel.setSizeFull();
        Label label = new Label("<b>" + component.getCaption() + "</b>", ContentMode.HTML);
        panel.addComponent(label);
        panel.addComponent(component);
        component.setCaption("");
        return panel;
    }

    private static Window getWindow() {
        Window window = new Window();
        window.setWidth("70%");
        window.setHeight("70%");
        window.center();
        window.setModal(true);

        return window;
    }

    private static String createCaption(StatEnum stast) {
        if (stast == StatEnum.KASSA) {
            return "Касса";
        } else if (stast == StatEnum.BASS) {
            return "ТСО";
        } else if (stast == StatEnum.P24) {
            return "Приват 24";
        } else if (stast == StatEnum.PFL) {
            return "Платежи физических лиц";
        } else if (stast == StatEnum.PUL) {
            return "Платежи юридических лиц";
        } else if (stast == StatEnum.P3700) {
            return manageTitle();
        }
        return "";
    }

    private static String manageTitle() {
        switch (manager.getLang()) {
            case GEO:
            case RU:
                return "Регулярный платеж";
            default:
                return "3700";
        }
    }
}
