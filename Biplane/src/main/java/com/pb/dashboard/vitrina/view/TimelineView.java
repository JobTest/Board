package com.pb.dashboard.vitrina.view;

import com.pb.dashboard.vitrina.CountryButtons;
import com.pb.dashboard.vitrina.core.config.ConfigManager;
import com.pb.dashboard.vitrina.core.config.Language;
import com.pb.dashboard.vitrina.core.country.CountrySelector;
import com.pb.dashboard.vitrina.timeline.TimelineManager;
import com.pb.dashboard.vitrina.timeline.view.InfoPanel;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.timeline.Timeline;
import com.vaadin.data.Container;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimelineView extends VerticalLayout implements View, CountrySelector {

    private TimelineManager timelineManager;
    private ConfigManager configManager;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy", new Locale("RU"));

    private static final long MILLIS_IN_DAY = 86400000L;
    private static final long MILLIS_IN_MONTH = 2629743830L;
    private static final long MILLIS_IN_YEAR = 31556926000L;

    private Component content;
    private Label periodLabel;
    private Timeline timeline;
    private InfoPanel infoPanel;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

    public TimelineView() {
        initManagers();
        setSizeFull();
        addStyleName("timeline-view");
        VerticalLayout row = new VerticalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        addComponent(row);
        setExpandRatio(row, 3);
        row.addComponent(createPanels());

//        updatePanelsInfo();
    }

    private void initManagers() {
        configManager = new ConfigManager();
        timelineManager = new TimelineManager(configManager);
    }

    private CssLayout createPanels() {
        CssLayout panel = new CssLayout();
        panel.setSizeFull();
        panel.addStyleName("layout-panel");
        content = createContent();
        panel.addComponent(content);
        content.setCaption("Платежи: Украина");
        return panel;
    }

    private Component createContent() {
        VerticalLayout dataView = new VerticalLayout();
        dataView.setStyleName("data-view");
        dataView.setSpacing(true);
        dataView.setMargin(true);
        dataView.addComponent(getHeader());
        initTimeline();
        dataView.addComponent(timeline);
        periodLabel = new Label();
        periodLabel.setSizeUndefined();
        periodLabel.setHeight("20px");
        dataView.addComponent(periodLabel);
        dataView.setComponentAlignment(periodLabel, Alignment.MIDDLE_CENTER);
        infoPanel = new InfoPanel();
        dataView.addComponent(infoPanel);
        return dataView;
    }

    public void initTimeline() {
        timeline = new Timeline();
        timeline.setStyleName("timeline-panel");
        timeline.setWidth("100%");
        timeline.setHeight("500px");
        timeline.setLocale(new Locale("RU"));
        timeline.setZoomLevelsCaption("Период:");
        timeline.addZoomLevel("Неделя", 7 * MILLIS_IN_DAY);
        timeline.addZoomLevel("Месяц", MILLIS_IN_MONTH);
        timeline.addZoomLevel("Квартал", 3 * MILLIS_IN_MONTH);
        timeline.addZoomLevel("Полугодие", 6 * MILLIS_IN_MONTH);
        timeline.addZoomLevel("Год", MILLIS_IN_YEAR);
        timeline.setGraphStacking(true);
        timeline.setGraphShadowsEnabled(false);
        timeline.setNoDataSourceCaption("Данные отсутствуют");
        timeline.setVerticalAxisNumberFormat("###,###,###");
        timeline.setUniformBarThicknessEnabled(true);

        //timeline.setVerticalAxisRange(0f, 900000.0f);

        timeline.setChartMode(Timeline.ChartMode.BAR);
        timeline.setChartModesVisible(true);
        timeline.setChartModesCaption("Тип графика");

        timeline.addListener(new Timeline.DateRangeListener() {
            @Override
            public void dateRangeChanged(Timeline.DateRangeChangedEvent event) {
                updatePanelsInfo();
            }
        });
    }

    public Thread runGetData() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                setTimelineDataSource(timelineManager.collectMetrics());
                updatePanelsInfo();
            }
        });
        thread.start();
        return thread;
    }

    public void setTimelineDataSource(List<Container.Indexed> list) {
        timeline.removeAllGraphDataSources();

        Container.Indexed paydeskDS = list.get(0);
        Container.Indexed terminalDS = list.get(1);
        Container.Indexed p24DS = list.get(2);
        Container.Indexed l3700DS = list.get(3);

        // Setup max value for Timeline Vertical Axis Range
        if (timelineManager.getLimit() > 0) {
            String l = "" + timelineManager.getLimit();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < l.length(); i++) {
                if (i < 2) sb.append(l.charAt(i));
                else sb.append("0");
            }
            float limit = (float) Integer.parseInt(sb.toString());
            timeline.setVerticalAxisRange(0f, limit);
        }

        // Add Data Sources
        timeline.addGraphDataSource(l3700DS,
                Timeline.PropertyId.TIMESTAMP,
                Timeline.PropertyId.VALUE);
        timeline.addGraphDataSource(p24DS,
                Timeline.PropertyId.TIMESTAMP,
                Timeline.PropertyId.VALUE);
        timeline.addGraphDataSource(terminalDS,
                Timeline.PropertyId.TIMESTAMP,
                Timeline.PropertyId.VALUE);
        timeline.addGraphDataSource(paydeskDS,
                Timeline.PropertyId.TIMESTAMP,
                Timeline.PropertyId.VALUE);

        timeline.setGraphCaption(paydeskDS, "Касса");
        timeline.setGraphCaption(terminalDS, "ТСО");
        timeline.setGraphCaption(p24DS, "Приват24");
        timeline.setGraphCaption(l3700DS, "3700");

        timeline.setGraphOutlineColor(paydeskDS, new SolidColor(128, 212, 223, 1));
        timeline.setGraphFillColor(paydeskDS, null);
        timeline.setVerticalAxisLegendUnit(paydeskDS, "шт");
        timeline.setGraphOutlineColor(terminalDS, new SolidColor(81, 197, 212, 1));
        timeline.setGraphFillColor(terminalDS, null);
        timeline.setVerticalAxisLegendUnit(terminalDS, "шт");
        timeline.setGraphOutlineColor(p24DS, new SolidColor(254, 197, 107, 1));
        timeline.setGraphFillColor(p24DS, null);
        timeline.setVerticalAxisLegendUnit(p24DS, "шт");
        timeline.setGraphOutlineColor(l3700DS, new SolidColor(234, 168, 58, 1));
        timeline.setGraphFillColor(l3700DS, null);
        timeline.setVerticalAxisLegendUnit(l3700DS, "шт");

        timeline.setBrowserOutlineColor(paydeskDS, new SolidColor(128, 212, 223, 1));
        timeline.setBrowserOutlineColor(terminalDS, new SolidColor(81, 197, 212, 1));
        timeline.setBrowserOutlineColor(p24DS, new SolidColor(254, 197, 107, 1));
        timeline.setBrowserOutlineColor(l3700DS, new SolidColor(234, 168, 58, 1));

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, -2);
        timeline.setVisibleDateRange(c.getTime(), today);
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

    public void updatePanelsInfo() {
        Date from = timeline.getVisibleSelectionStart();
        Date to = timeline.getVisibleSelectionEnd();
        periodLabel.setValue(String.format("Выбранный период: %s - %s", sdf.format(from), sdf.format(to)));
        infoPanel.setData(timelineManager.getInfoPanelData(from, to));
    }

    @Override
    public void updateCountry(Language lang) {
        configManager.setLang(lang);
        content.setCaption("Платежи: " + lang.getName());
    }

    @Override
    public void updateData() {
        setTimelineDataSource(timelineManager.collectMetrics());
    }
}
