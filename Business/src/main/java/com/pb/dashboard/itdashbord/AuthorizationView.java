package com.pb.dashboard.itdashbord;

import com.pb.dashboard.itdashbord.db.DataHolder;
import com.pb.dashboard.itdashbord.table.TableBuilder;
import com.pb.dashboard.itdashbord.table.authorize.AuthorizeBean;
import com.pb.dashboard.itdashbord.table.authorize.AuthorizePhoneBean;
import com.pb.dashboard.itdashbord.table.payment.WeekCellRender;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.util.Collections;
import java.util.List;

public class AuthorizationView extends VerticalLayout implements View {

    private static final long serialVersionUID = 8878715222884472024L;
    private String[] columnsName = {"Дата", "Операций в ТСО", "Авторизация по QR-коду", "Без авторизации", "%", "Авторизация по карте",
            "Авторизация по телефону", "телефон,%"};
    private String[] authoName = {"Дата", "Более 1 СМС в день", "Более 3 СМС в день", "Более 5 СМС в день", "10 и более СМС в день"};
    private WeekCellRender weekCellRender;
    private AuthorizePhoneBean phoneBean;
    private TableBuilder tableBuilder;
    private AuthorizeBean authBean;
    private Table alldata;
    private Table currentDateData;
    private Table authhorizeData;

    public AuthorizationView() {
        init();
        setSizeFull();
        createToolBoxPanel();
        initTables();
        createMainContent();
    }

    private void init() {
        Object[] holder = DataHolder.getAuthorizationData();
        weekCellRender = new WeekCellRender();
        phoneBean = new AuthorizePhoneBean(holder);
        tableBuilder = new TableBuilder();
        authBean = new AuthorizeBean(holder);
    }

    public void createToolBoxPanel() {
        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setSpacing(true);
        top.addStyleName("toolbar");
        top.setMargin(true);
        addComponent(top);
        final Label title = new Label("Обязательная авторизация");
        title.setSizeUndefined();
        title.addStyleName("h2");
        top.addComponent(title);
        top.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        top.setExpandRatio(title, 1);
    }

    public void createMainContent() {
        HorizontalLayout row = new HorizontalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        addComponent(row);
        setExpandRatio(row, 1.5f);

        TabSheet tabSheet = new TabSheet();
        tabSheet.setHeight(100.0f, Unit.PERCENTAGE);
        tabSheet.addTab(createPanel(currentDateData), "Текущий месяц");
        tabSheet.addTab(createPanel(alldata), "Общая");
        tabSheet.addTab(createPanel(authhorizeData), "Отправленные СМС");
        row.addComponent(createPanel(tabSheet));

    }

    private CssLayout createPanel(Component content) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();
        panel.addComponent(content);
        return panel;
    }

    private void initTables() {
        alldata = tableBuilder.buildTableproperty(false, columnsName, "");
        currentDateData = tableBuilder.buildTableproperty(false, columnsName, "");
        authhorizeData = tableBuilder.buildTableproperty(false, authoName, "Количество телефонов по которым отправленно СМС");
        updateDataReloadContect();
//	/*
//	 * set Renders
//	 */
        alldata.setCellStyleGenerator(weekCellRender.cellEditingNoDayOfWeek());
        currentDateData.setCellStyleGenerator(weekCellRender.cellEditingNoDayOfWeek());
        authhorizeData.setCellStyleGenerator(weekCellRender.cellEditingNoDayOfWeek());
    }

    private void updateDataReloadContect() {
        List<Object[]> paymentsTCO = authBean.getPaymentsCurrentData();
        Collections.reverse(paymentsTCO);
        fillTable(paymentsTCO, authBean.getSum(), currentDateData, columnsName);
        List<Object[]> payments = authBean.getPayments();
        Collections.reverse(payments);
        fillTable(payments, authBean.getSumAll(), alldata, columnsName);

        List<Object[]> phones = phoneBean.getPayments();
        Collections.reverse(phones);
        fillTable(phones, phoneBean.getSumAll(), authhorizeData, authoName);

    }

    private void fillTable(List<Object[]> payments, Object[] sumMetrics, Table table, String[] columnsName) {
        table.removeAllItems();
        table.setColumnFooter("Дата", "Всего:");
        for (int i = 0; i < payments.size(); i++) {
            table.addItem(payments.get(i), i + 1);
        }
        for (int i = 1; i < columnsName.length; i++) {
            table.setColumnFooter(columnsName[i], (String) sumMetrics[i]);
        }
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

}
