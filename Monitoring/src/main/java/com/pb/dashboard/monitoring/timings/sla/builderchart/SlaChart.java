package com.pb.dashboard.monitoring.timings.sla.builderchart;

import com.pb.dashboard.monitoring.context.ContextFactory;
import com.pb.dashboard.monitoring.timings.charts.reglament.ChartPeriodManagerI;
import com.pb.dashboard.monitoring.timings.sla.SlaModelI;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.Style;

/**
 * Created by vlad
 * Date: 07.11.14_14:25
 */
public class SlaChart extends Chart implements SlaChartI {

    private static final long serialVersionUID = -2557475407557262691L;

    private ChartPeriodManagerI periodManager = createPeriodManager();

    public SlaChart() {
        init();
    }

    private ChartPeriodManagerI createPeriodManager() {
        return ContextFactory.getContext().getBean(ChartPeriodManagerI.class);
    }

    private void init() {
        setHeight("400px");
        setWidth("100%");

        initCredits();
        initXAxis();
        initFormatter();
    }

    private void initCredits() {
        Configuration conf = getConfiguration();
        conf.disableCredits();
    }

    private void initXAxis() {
        Axis xAxis = getConfiguration().getxAxis();
        xAxis.setType(AxisType.DATETIME);
        xAxis.setLabels(createLabels());
        xAxis.setAllowDecimals(true);
    }

    private Labels createLabels() {
        Labels labels = new Labels();
        labels.setRotation(-60);
        labels.setAlign(HorizontalAlign.RIGHT);
        labels.setStyle(createStyle());
        return labels;
    }

    private Style createStyle() {
        Style style = new Style();
        style.setFontSize("12px");
        style.setFontFamily("Verdana, sans-serif");
        return style;
    }

    private void initFormatter() {
        String format = "function() {" +
                " var date = new Date(this.x);" +
                " var options = { timeZone: 'UTC'};" +
                " var format = date.toLocaleString('ua-UA',options);" +
                " return '<b>' + this.series.name + '</b><br/>' + format + ': <b>' + this.y + '</b>';" +
                "}";
        Tooltip tooltip = getConfiguration().getTooltip();
        tooltip.setFormatter(format);
    }

    public void setExtremes(SlaModelI model) {
        periodManager.setExtremes(this, model);
        periodManager.setTickInterval(this, model);
    }
}