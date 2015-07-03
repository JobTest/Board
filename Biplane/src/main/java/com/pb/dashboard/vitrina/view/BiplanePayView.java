package com.pb.dashboard.vitrina.view;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.pb.dashboard.vitrina.CountryButtons;
import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.core.config.ConfigManager;
import com.pb.dashboard.vitrina.core.config.Language;
import com.pb.dashboard.vitrina.core.country.CountrySelector;
import com.pb.dashboard.vitrina.core.db.BiplaneDBManager;
import com.pb.dashboard.vitrina.core.table.TableBuilder;
import com.pb.dashboard.vitrina.core.table.WindowsDataCreatorBean;
import com.pb.dashboard.vitrina.payment.data.StatEnum;
import com.pb.dashboard.vitrina.payview.DiffHolder;
import com.pb.dashboard.vitrina.payview.PayViewCellRenderer;
import com.pb.dashboard.vitrina.representation.payment.*;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BiplanePayView extends VerticalLayout implements View, CountrySelector {

    private static final Logger LOG = Logger.getLogger(BiplanePayView.class);
    private static final long serialVersionUID = -1744455941100836080L;
    private static final int PERIOD = 15 * 60 * 1000;//900000
    private ASEDBManager dm = ASEDBManager.getInstance();
    private BiplaneDBManager dbManager = new BiplaneDBManager();
    private final String[] columns = {"Период", "Прием", "Наличные",
            "Безналичные", "Задолженность", "Физ.лица", "Юр.лица"};
    private Table kassa;
    private Table p24;
    private Table bass;
    private Table p3700;
    private Table totalTable;
    private Sub24TableBean p24Bean = new Sub24TableBean();
    private SubKassaTableBean kassaBean = new SubKassaTableBean();
    private BassTableBean bassBean = new BassTableBean();
    private P3700TableBean p3700TableBean = new P3700TableBean();
    private TotalSumTableBean totalSumBean = new TotalSumTableBean();
    private Label p3700tableLabel;
    private TableBuilder builder = new TableBuilder();
    private String lang = "Украина";
    private ConfigManager manager;

    private List<PayViewCellRenderer> tableRenderers = new ArrayList<PayViewCellRenderer>();
    private List<DiffHolder> diffs = new ArrayList<DiffHolder>();

    private Component content;

    public BiplanePayView() {
        setConfigManager();

        setSizeFull();
        addStyleName("dashboard-view");
        VerticalLayout row = new VerticalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(false);
        addComponent(row);
        setExpandRatio(row, 2);

        DBUpDate();
        initTables();
        row.addComponent(createPanels(kassa, bass, p24, p3700, totalTable));
    }

    private void setConfigManager() {
        manager = new ConfigManager();
        totalSumBean.setConfigManager(manager);
        p24Bean.setConfigManager(manager);
        kassaBean.setConfigManager(manager);
        bassBean.setConfigManager(manager);
        p3700TableBean.setConfigManager(manager);
    }

    private CssLayout createPanels(Table... tables) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();
        content = createContent(tables);
        panel.addComponent(content);
        content.setCaption("Прием Платежей: " + lang);
        return panel;
    }

    private Component createContent(final Table... tables) {
        VerticalLayout dataView = new VerticalLayout();
        dataView.addStyleName("data-view");
        dataView.addComponent(getHeader());
        for (Table component : tables) {
            if (component.getCaption().equals("")) {
                Label label = new Label("<b> Всего</b>", ContentMode.HTML);
                component.setFooterVisible(false);
                dataView.addComponent(label);
                dataView.addComponent(component);
            } else if (component == p3700) {
                p3700tableLabel = new Label(
                        "<b>" + component.getCaption() + "</b>",
                        ContentMode.HTML);
                dataView.addComponent(p3700tableLabel);
                dataView.addComponent(component);
                component.setCaption("");
            } else {
                Label label = new Label(
                        "<b>" + component.getCaption() + "</b>",
                        ContentMode.HTML);
                dataView.addComponent(label);
                dataView.addComponent(component);
                component.setCaption("");
            }
        }
        return dataView;
    }

    private HorizontalLayout getHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setHeight("20px");
        CountryButtons countryButtons = new CountryButtons(this);
        header.addComponent(countryButtons);
        header.setComponentAlignment(countryButtons, Alignment.MIDDLE_CENTER);
        return header;
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

    private void initTables() {
        for (int i = 0; i < 4; i++) {
            tableRenderers.add(new PayViewCellRenderer(columns));
            diffs.add(new DiffHolder());
        }

        kassa = builder.buildTableproperty(false, columns, "Касса");
        kassa.addItemClickListener(getListener(StatEnum.KASSA));
        kassa.setCellStyleGenerator(tableRenderers.get(0).getGenerator());

        bass = builder.buildTableproperty(true, columns, "ТСО");
        bass.addItemClickListener(getListener(StatEnum.BASS));
        bass.setCellStyleGenerator(tableRenderers.get(1).getGenerator());

        p24 = builder.buildTableproperty(true, columns, "Приват 24");
        p24.addItemClickListener(getListener(StatEnum.P24));
        p24.setCellStyleGenerator(tableRenderers.get(2).getGenerator());

        p3700 = builder.buildTableproperty(true, columns, titleManagement());
        p3700.addItemClickListener(getListener(StatEnum.P3700));
        p3700.setCellStyleGenerator(tableRenderers.get(3).getGenerator());

        totalTable = builder.buildTableproperty(true, columns, "");
        fillTables();
    }

    private String titleManagement() {
        switch (manager.getLang()) {
            case GEO:
            case RU:
                return "Регулярный платеж";
            default:
                return "3700";
        }
    }

    private void fillTables() {
        prepareRenderers();
        fillTable(kassaBean.getSumMetrics(), kassaBean.getPayments(), kassa);
        fillTable(p24Bean.getSumMetrics(), p24Bean.getPayments(), p24);
        fillTable(bassBean.getSumMetrics(), bassBean.getPayments(), bass);
        fillTable(p3700TableBean.getSumMetrics(), p3700TableBean.getPayments(), p3700);
        totalTable.removeAllItems();
        totalTable.addItem(totalSumBean.populatePayments().get(0), 1);
    }

    private void prepareRenderers() {
        Integer[] thresholds = dbManager.getHourlyThreshold(lang);
        kassaBean.fillLists();
        bassBean.fillLists();
        p24Bean.fillLists();
        p3700TableBean.fillLists();
        for (int i = 0; i < thresholds.length; i++) {
            List<Object[]> metrics = null;
            List<Object[]> monthOldMetrics = null;
            if (i == 0) {
                metrics = kassaBean.getPayments();
                monthOldMetrics = kassaBean.monthOldPayments;
            } else if (i == 1) {
                metrics = bassBean.getPayments();
                monthOldMetrics = bassBean.monthOldPayments;
            } else if (i == 2) {
                metrics = p24Bean.getPayments();
                monthOldMetrics = p24Bean.monthOldPayments;
            } else if (i == 3) {
                metrics = p3700TableBean.getPayments();
                monthOldMetrics = p3700TableBean.monthOldPayments;
            }
            if (thresholds[i] != null) {
                tableRenderers.get(i).setDiscrepancy(monthOldMetrics, diffs.get(i).getOldMetrics(), metrics,
                        thresholds[i]);
            }
        }
    }

    private void DBUpDate() {
        //updateDataReloadContent(true);
        Refresher refresher = new Refresher();
        refresher.setRefreshInterval(PERIOD);
        addExtension(refresher);
        refresher.addListener(new RefreshListener() {
            private static final long serialVersionUID = 4151630300988516091L;

            @Override
            public void refresh(final Refresher source) {
                try {
                    updateDataReloadContent(true);
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                    LOG.error("Refresher - " + source);
                }
            }
        });
    }

    private void updateDataReloadContent(boolean update) {
        if (update) {
            saveOldTableData();
            dm.updateData();
        }
        fillTables();
    }

    private void saveOldTableData() {
        diffs.get(0).setOldMetrics(kassaBean.getPayments());
        diffs.get(1).setOldMetrics(bassBean.getPayments());
        diffs.get(2).setOldMetrics(p24Bean.getPayments());
        diffs.get(3).setOldMetrics(p3700TableBean.getPayments());
    }

    private void fillTable(Object[] sumMetrics, List<Object[]> payments,
                           Table table) throws UnsupportedOperationException {
        table.removeAllItems();
        table.setColumnFooter("Период", "Всего:");
        for (int i = 0; i < payments.size(); i++) {
            table.addItem(payments.get(i), (i + 1));
        }
        for (int i = 1; i < columns.length; i++) {
            table.setColumnFooter(columns[i], (String) sumMetrics[i]);
        }
    }

    private ItemClickEvent.ItemClickListener getListener(final StatEnum statEnum) {
        return new ItemClickEvent.ItemClickListener() {
            private static final long serialVersionUID = -2463897562008820567L;

            public void itemClick(ItemClickEvent event) {
                if (event.getButton().equals(MouseEventDetails.MouseButton.RIGHT)) {
                    Window window = WindowsDataCreatorBean.createWindow(statEnum, manager);
                    UI.getCurrent().addWindow(window);
                } else {
                    Notification.show("Для развернутой информации нажмите правую кнопку мыши.",
                            Type.TRAY_NOTIFICATION);
                }
            }
        };
    }

    private void resetRenderers() {
        for (PayViewCellRenderer renderer : tableRenderers) {
            renderer.reset();
        }
    }

    @Override
    public void updateCountry(Language lang) {
        manager.setLang(lang);
        content.setCaption("Прием Платежей: " + lang.getName());
        p3700tableLabel.setValue("<b>" + titleManagement() + "</b>");
        resetRenderers();
    }

    @Override
    public void updateData() {
        updateDataReloadContent(false);
    }
}
