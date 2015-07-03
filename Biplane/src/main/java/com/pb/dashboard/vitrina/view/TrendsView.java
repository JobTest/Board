package com.pb.dashboard.vitrina.view;

import com.github.wolfie.refresher.Refresher;
import com.pb.dashboard.vitrina.CountryButtons;
import com.pb.dashboard.vitrina.core.config.ConfigManager;
import com.pb.dashboard.vitrina.core.config.Language;
import com.pb.dashboard.vitrina.core.country.CountrySelector;
import com.pb.dashboard.vitrina.trends.TrendsInfoPanelManager;
import com.pb.dashboard.vitrina.trends.TrendsManager;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;

public class TrendsView extends VerticalLayout implements View, CountrySelector {

    private static final Logger LOG = Logger.getLogger(TrendsView.class);
    private static final int PERIOD = 15 * 60 * 1000;
    private Component content;

    private TrendsInfoPanelManager infoPanelManager = new TrendsInfoPanelManager();
    private ConfigManager configManager;
    private TrendsManager trendsManager = new TrendsManager(infoPanelManager);

    public TrendsView() {
        setConfigManager();

        setSizeFull();
        addStyleName("timeline-view");
        VerticalLayout row = new VerticalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        addComponent(row);
        setExpandRatio(row, 3);
        row.addComponent(createPanels());
        beginTrendTask();
    }

    private void setConfigManager() {
        configManager = new ConfigManager();
        trendsManager.setConfigManager(configManager);
    }

    private void beginTrendTask() {
        updateTrends();
        Refresher refresher = new Refresher();
        refresher.setRefreshInterval(PERIOD);
        addExtension(refresher);
        refresher.addListener(new Refresher.RefreshListener() {
            private static final long serialVersionUID = 4151630300988516091L;

            @Override
            public void refresh(final Refresher source) {
                try {
                    updateTrends();
                } catch (Exception e) {
                    LOG.error("[log Trends] :" + e.getCause().getMessage());
                }
            }
        });
    }

    private void updateTrends() {
        trendsManager.collectMetrics();

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
        dataView.addComponent(infoPanelManager.getPaydeskPanel());
        dataView.addComponent(infoPanelManager.getTerminalPanel());
        dataView.addComponent(infoPanelManager.getP24Panel());
        dataView.addComponent(infoPanelManager.get3700Panel());
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
    public void updateCountry(Language lang) {
        configManager.setLang(lang);
        content.setCaption("Платежи: " + lang.getName());
    }

    @Override
    public void updateData() {
        trendsManager.collectMetrics();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

}
