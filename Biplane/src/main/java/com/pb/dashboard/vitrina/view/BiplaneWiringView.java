package com.pb.dashboard.vitrina.view;

import com.github.wolfie.refresher.Refresher;
import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.core.table.TableBuilder;
import com.pb.dashboard.vitrina.payment.data.StatEnum;
import com.pb.dashboard.vitrina.representation.operday.OperDayBiplaneBean;
import com.pb.dashboard.vitrina.representation.other.FormedProvodBean;
import com.pb.dashboard.vitrina.representation.other.FormedRegisterBean;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.util.List;

public class BiplaneWiringView extends VerticalLayout implements View {

    private static final int PERIOD = 15 * 60 * 1000;
    private ASEDBManager dm = ASEDBManager.getInstance();
    private TableBuilder builder = new TableBuilder();
    private Table provodki;
    private Table register;
    private Table operDayKassa;
    private Table operDayBass;
    private Table operDayP24;
    private FormedProvodBean formedProvodBean = new FormedProvodBean();
    private FormedRegisterBean formedRegisterBean = new FormedRegisterBean();
    private OperDayBiplaneBean operDayBiplaneBean = new OperDayBiplaneBean();
    private String[] columnsProvodki = {"Выгруженные", "Проведенные в ОДБ", "Отложенные", "Не выгруженные", "Проводки юридических лиц"};
    private String[] columnsRegister = {"Наименование", "Не отправленные", "Отправленные"};
    private String[] columnsOperDay = {"Наименование", "Открыто", "Закрыто"};

    public BiplaneWiringView() {
        setSizeFull();
        addStyleName("dashboard-view");
        VerticalLayout row = new VerticalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        addComponent(row);

        setExpandRatio(row, 3);
        DBUpDate();
        initTables();
        row.addComponent(createPanels(provodki, operDayKassa, operDayBass, operDayP24, register));
    }

    private CssLayout createPanels(Table... tables) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();
        Component content = createContent(tables);
        panel.addComponent(content);
        content.setCaption("Опердни - Проводки - Реестры");
        return panel;
    }

    private Component createContent(Table... tables) {
        VerticalLayout dataView = new VerticalLayout();
        dataView.addStyleName("data-view");
        dataView.addComponent(getHeader());
        for (Table component : tables) {
            Label label = new Label("<b>" + component.getCaption() + "</b>", ContentMode.HTML);
            dataView.addComponent(label);
            dataView.addComponent(component);
            component.setCaption("");
        }
        return dataView;
    }

    private HorizontalLayout getHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setHeight("20px");
        Button configureUA = new Button();
        configureUA.addStyleName("borderless");
        configureUA.addStyleName("small");
        configureUA.addStyleName("icon-only");
        configureUA.addStyleName("configure");
        configureUA.setIcon(new ThemeResource("icons/ua.png"));
        header.addComponent(configureUA);
        header.setComponentAlignment(configureUA, Alignment.MIDDLE_CENTER);
        return header;
    }

    private void initTables() {
        provodki = builder.buildTableproperty(false, columnsProvodki, "Формирование проводок");
        updateData(provodki, StatEnum.PROVODKI);
        register = builder.buildTableproperty(false, columnsRegister, "Формирование реестров");
        updateData(register, StatEnum.REGISTER);
        operDayKassa = builder.buildTableproperty(false, columnsOperDay, "Касса-опердни");
        updateData(operDayKassa, StatEnum.OPER_DAY_KASSA);
        operDayBass = builder.buildTableproperty(false, columnsOperDay, "ТСО-опердни");
        updateData(operDayBass, StatEnum.OPER_DAY_BASS);
        operDayP24 = builder.buildTableproperty(false, columnsOperDay, "Приват 24-опердни");
        updateData(operDayP24, StatEnum.OPER_DAY_P24);

    }

    private void updateData(Table table, StatEnum stast) {
        if (stast == StatEnum.PROVODKI) {
            fillTable(formedProvodBean.getData(), formedProvodBean.getSum(), table);
        } else if (stast == StatEnum.REGISTER) {
            fillTable(formedRegisterBean.getRegs(), null, table);
        } else if (stast == StatEnum.OPER_DAY_KASSA) {
            fillTable(operDayBiplaneBean.getOperListKassa(), operDayBiplaneBean.getKassaSum(), table);
        } else if (stast == StatEnum.OPER_DAY_BASS) {
            fillTable(operDayBiplaneBean.getOperListBass(), operDayBiplaneBean.getBassSum(), table);
        } else if (stast == StatEnum.OPER_DAY_P24) {
            fillTable(operDayBiplaneBean.getOperListP24(), operDayBiplaneBean.getP24Sum(), table);
        }
    }

    private void DBUpDate() {
        Refresher refresher = new Refresher();
        refresher.setRefreshInterval(PERIOD);
        refresher.addListener(new Refresher.RefreshListener() {
            private static final long serialVersionUID = 4752012693641297883L;
            @Override
            public void refresh(final Refresher source) {
                try {
                    dm.updateData();
                    updateData(provodki, StatEnum.PROVODKI);
                    updateData(register, StatEnum.REGISTER);
                    updateData(operDayKassa, StatEnum.OPER_DAY_KASSA);
                    updateData(operDayBass, StatEnum.OPER_DAY_BASS);
                    updateData(operDayP24, StatEnum.OPER_DAY_P24);
                } catch (Exception e) {
                }
            }
        });
        addExtension(refresher);
    }

    private void fillTable(List<Object[]> payments, Object[] sumMetrics, Table table) throws UnsupportedOperationException {
        table.removeAllItems();
        for (int i = 0; i < payments.size(); i++) {
            table.addItem(payments.get(i), new Integer(i + 1));
        }
        if (sumMetrics != null) {
            for (int i = 0; i < sumMetrics.length; i++) {
                table.setColumnFooter(table.getColumnHeaders()[i], (String) sumMetrics[i]);
            }
        } else {
            table.setFooterVisible(false);
        }
    }

    private static final long serialVersionUID = -1744455941100836080L;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}