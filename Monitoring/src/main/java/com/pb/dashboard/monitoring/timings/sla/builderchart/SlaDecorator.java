package com.pb.dashboard.monitoring.timings.sla.builderchart;

import com.pb.dashboard.dao.entity.vitrinametrics.SlaInterfaceI;
import com.pb.dashboard.monitoring.timings.tabsheet.ChartDetail;
import com.vaadin.data.Property;
import com.vaadin.ui.CheckBox;

import java.util.List;

/**
 * Created by vlad
 * Date: 25.03.15_10:09
 */
public class SlaDecorator {

    private static final String SHOW_ERROR = "Отображать ошибки";
    private CheckBox showError;

    private PercentChart percentChart;
    private CountsChart countsChart;
    private TimingsChart timingsChart;

    private List<SlaInterfaceListener> listeners;

    public SlaDecorator() {
        initCheckBoxError();
        initCharts();
        initListeners();
    }

    private void initCheckBoxError() {
        showError = new CheckBox(SHOW_ERROR);
        showError.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = 5005181858674940186L;

            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                Boolean value = showError.getValue();
                if (value) {
                    countsChart.setShowType(CountsType.ERROR);
                } else {
                    countsChart.setShowType(CountsType.COUNT);
                }
            }
        });
    }

    private void initCharts() {
        percentChart = new PercentChart();
        countsChart = new CountsChart();
        timingsChart = new TimingsChart();
    }

    private void initListeners() {
        listeners = countsChart.getListeners();
        listeners.add(countsChart);
        listeners.add(percentChart);
        listeners.add(timingsChart);
    }

    public void setSelectedSla(SlaInterfaceI selectedSla) {
        for (SlaInterfaceListener listener : listeners) {
            listener.change(selectedSla);
        }
    }

    public CheckBox getShowError() {
        return showError;
    }

    public TimingsChart getTimingsChart() {
        return timingsChart;
    }

    public PercentChart getPercentChart() {
        return percentChart;
    }

    public CountsChart getCountsChart() {
        return countsChart;
    }

    public void setChartDetail(ChartDetail chartDetail) {
        countsChart.setChartDetail(chartDetail);
    }
}
