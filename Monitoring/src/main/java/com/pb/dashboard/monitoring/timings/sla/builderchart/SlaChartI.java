package com.pb.dashboard.monitoring.timings.sla.builderchart;

import com.pb.dashboard.monitoring.timings.sla.SlaModelI;
import com.vaadin.addon.charts.ChartSelectionListener;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.ui.Component;

/**
 * Created by vlad
 * Date: 28.04.15_10:17
 */
public interface SlaChartI extends Component {

    public void setExtremes(SlaModelI model);

    Configuration getConfiguration();

    public void drawChart();

    void addChartSelectionListener(ChartSelectionListener listener);

}