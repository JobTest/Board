package com.pb.dashboard.monitoring.timings.charts.reglament;

import com.vaadin.addon.charts.Chart;

/**
 * Created by vlad
 * Date: 21.04.15_17:01
 */

public interface ChartPeriodManagerI {

    public void setExtremes(Chart chart, DateModelI model);

    public void setTickInterval(Chart chart, DateModelI model);

}
