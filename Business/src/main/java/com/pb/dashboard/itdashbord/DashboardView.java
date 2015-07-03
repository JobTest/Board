package com.pb.dashboard.itdashbord;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.pb.dashboard.itdashbord.chart.ChartHolder;
import com.pb.dashboard.itdashbord.db.ASEDBManager;
import com.pb.dashboard.itdashbord.table.TableBuilder;
import com.pb.dashboard.itdashbord.table.payment.CellRender;
import com.pb.dashboard.itdashbord.table.payment.TCOTableBean;
import com.pb.dashboard.itdashbord.table.payment.TcoTypeTableBean;
import com.pb.dashboard.itdashbord.table.payment.WeekCellRender;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class DashboardView extends VerticalLayout implements View {

    private static final Logger LOG = Logger.getLogger(DashboardView.class);
    private String[] columnsName = {"Дата", "День недели",
            "Кол-во платежей в ТСО", "ТСО через меню \"Мои платежи\"", "%"};
    private TableBuilder tableBuilder = new TableBuilder();
    private Table tcoTable;
    private Table tcoTableAll;
    private Table tcoType;
    private WeekCellRender weekCellRender = new WeekCellRender();
    private CellRender cellRendering = new CellRender();
    private TcoTypeTableBean typeTableBean = new TcoTypeTableBean();
    private TCOTableBean tcoTableBean = new TCOTableBean();
    ASEDBManager mDb = ASEDBManager.getInstance();
    Button notify;

    private static final int PERIOD = 600 * 1000;

    public DashboardView() {
        setSizeFull();
        // addStyleName("dashboard-view");
        createToolBoxPanel();
        dbUpdate();
        initTables();
        createMainContent();
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
        tabSheet.addTab(createPanel(tcoTable), "Текущий месяц");
        tabSheet.addTab(createPanel(tcoTableAll), "Общие данные");
        tabSheet.addTab(createPanel(tcoType), "По типам платежей");
        ChartHolder charts = new ChartHolder();
        tabSheet.addTab(createPanels(charts.getChart(), charts.getPieChart(), charts.getLineChart()), "Графики");
        row.addComponent(createPanel(tabSheet));

    }

    public void createToolBoxPanel() {
        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setSpacing(true);
        top.addStyleName("toolbar");
        top.setMargin(true);
        addComponent(top);
        final Label title = new Label("Мои Платежи - ТСО");
        title.setSizeUndefined();
        title.addStyleName("h2");
        top.addComponent(title);
        top.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        top.setExpandRatio(title, 1);

        notify = new Button("2");
        notify.setDescription("Notifications (2 unread)");
        // notify.addStyleName("borderless");
        notify.addStyleName("notifications");
        notify.addStyleName("unread");
        notify.addStyleName("icon-only");
        notify.addStyleName("icon-bell");
        notify.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                event.getButton().removeStyleName("unread");
                event.getButton().setDescription("Notifications");
                if (notifications != null && notifications.getUI() != null)
                    notifications.close();
                buildNotifications(event);
                getUI().addWindow(notifications);
                notifications.focus();
            }
        });
        top.addComponent(notify);
        top.setComponentAlignment(notify, Alignment.MIDDLE_LEFT);
    }

    private CssLayout createPanel(Component content) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();
        panel.addComponent(content);
        return panel;
    }

    private CssLayout createPanels(Component... content) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        // panel.setSizeFull();
        for (Component cmp : content) {
            panel.addComponent(cmp);
        }
        return panel;
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

    private void dbUpdate() {
        //right
        Refresher refresher = new Refresher();
        refresher.setRefreshInterval(PERIOD);
        addExtension(refresher);
        refresher.addListener(new RefreshListener() {
            private static final long serialVersionUID = 4151630300988516091L;

            @Override
            public void refresh(final Refresher source) {
                try {
                    updateDataReloadContect();
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                }
            }
        });
    }

    private void updateDataReloadContect() {
        List<Object[]> paymentsTCO = tcoTableBean.getPaymentsTCO();
        Collections.reverse(paymentsTCO);
        fiilTable(paymentsTCO, tcoTableBean.getSum(), tcoTable, columnsName);
        List<Object[]> payments = tcoTableBean.getPayments();
        Collections.reverse(payments);
        fiilTable(payments, tcoTableBean.getSumAll(), tcoTableAll, columnsName);
        fiilTable(typeTableBean.getPayments(), typeTableBean.getSum(), tcoType, typeTableBean.getColumns());
    }

    private void fiilTable(List<Object[]> payments, Object[] sumMetrics,
                           Table table, String[] columnsName) throws UnsupportedOperationException {
        table.removeAllItems();
        table.setColumnFooter("Дата", "Всего:");
        for (int i = 0; i < payments.size(); i++) {
            table.addItem(payments.get(i), new Integer(i + 1));
        }
        for (int i = 1; i < columnsName.length; i++) {
            table.setColumnFooter(columnsName[i], (String) sumMetrics[i]);
        }
    }

    private void initTables() {
        tcoTable = tableBuilder.buildTableproperty(false, columnsName, "");
        tcoTableAll = tableBuilder.buildTableproperty(false, columnsName, "");

        /**
         */
        String[] cl = typeTableBean.getColumns();
        tcoType = tableBuilder.buildTableproperty(false, typeTableBean.getColumns(), "Количество платежей по шаблонам ");
        // tcoType.setSizeFull();
        tcoType.setColumnCollapsingAllowed(true);
        tcoType.setColumnCollapsible(cl[0], false);
        tcoType.setColumnCollapsible(cl[1], true);
        tcoType.setColumnCollapsible(cl[2], true);
        tcoType.setColumnCollapsible(cl[3], true);
        tcoType.setColumnCollapsible(cl[4], true);

        /**
         *
         */
        updateDataReloadContect();
    /*
     * set Renders
	 */
        tcoTableAll.setCellStyleGenerator(weekCellRender.cellEditing());
        tcoTable.setCellStyleGenerator(cellRendering.cellEditing("01.2014", tcoTableBean, tcoTable));
    }

    Window notifications;

    private void buildNotifications(ClickEvent event) {
        notifications = new Window("Внимание!");
        VerticalLayout l = new VerticalLayout();
        l.setMargin(true);
        l.setSpacing(true);
        notifications.setContent(l);
        notifications.setWidth("280px");
        notifications.setHeight("80%");

        notifications.addStyleName("notifications");
        notifications.setClosable(true);
        notifications.setResizable(false);
        notifications.setDraggable(false);
        notifications.setCloseShortcut(KeyCode.ESCAPE, null);
        notifications.setPositionX(event.getClientX() - event.getRelativeX() - 50);
        notifications.setPositionY(event.getClientY() - event.getRelativeY());

        List<String> mesasges = mDb.getMessages();
        for (String msg : mesasges) {
            Label label = new Label(
                    "<hr><b>" + msg, ContentMode.HTML);
            l.addComponent(label);
        }
        notify.setDescription("Предупреждений (" + mesasges.size());
        notify.setCaption(mesasges.size() + "");
    }

}