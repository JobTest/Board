package com.pb.dashboard.monitoring.timings.sla.builderchart;

import com.pb.dashboard.dao.entity.vitrinametrics.SlaCountI;
import com.pb.dashboard.dao.entity.vitrinametrics.SlaInterfaceI;
import com.pb.dashboard.monitoring.timings.sla.SlaModelI;
import com.vaadin.addon.charts.ChartSelectionEvent;
import com.vaadin.addon.charts.ChartSelectionListener;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 27.03.15_14:17
 */
public class PercentChart extends VerticalLayout implements SlaInterfaceListener {

    public static final String TITLE = "SL-показатели в разрезе операций";
    public static final String RESET_ZOOM = "Reset zoom";
    public static final int LIMIT_MIN = 0;
    public static final int LIMIT_MAX = 100;

    public static final int PLOT_LINE_WIDTH = 3;
    public static final int PLOT_LINE_VALUE = 95;
    public static final SolidColor PLOT_LINE_COLOR = SolidColor.RED;
    private static final long serialVersionUID = -5921004716546633681L;

    private SlaChart chart = new SlaChart();
    private Button button;

    private Map<SlaInterfaceI, DataSeries> seriesMap = new LinkedHashMap<>();
    private SlaInterfaceI selectedSla;

    private void initResetButton() {
        button = new Button(RESET_ZOOM);
        button.setEnabled(false);
        button.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = -7866640955270904559L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                button.setEnabled(false);
                resetZoom();
            }
        });

        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("100%");
        layout.addComponent(button);
        layout.setComponentAlignment(button, Alignment.BOTTOM_RIGHT);
        addComponent(layout);
        addComponent(chart);
    }

    public PercentChart() {
        init();
        initResetButton();
    }

    private void init() {
        chart.setHeight("250px");
        Configuration conf = chart.getConfiguration();
        conf.setTitle(TITLE);

        YAxis yAxis = conf.getyAxis();
        yAxis.setTitle(createTitle());
        yAxis.getLabels().setFormatter("this.value+'%'");
        yAxis.getLabels().getStyle().setColor(SolidColor.GREEN);
        yAxis.setExtremes(LIMIT_MIN, LIMIT_MAX);
        yAxis.setPlotLines(new PlotLine(PLOT_LINE_VALUE, PLOT_LINE_WIDTH, PLOT_LINE_COLOR));

        Labels labels = conf.getxAxis().getLabels();
        labels.setFormatter("");
        labels.setFormat("");

        chart.getConfiguration().getChart().setZoomType(ZoomType.Y);

        chart.addChartSelectionListener(new ChartSelectionListener() {
            private static final long serialVersionUID = -1988208946642754572L;

            @Override
            public void onSelection(ChartSelectionEvent event) {
                YAxis yAxis = chart.getConfiguration().getyAxis();
                double start = around(event.getValueStart(), 2);
                double end = around(event.getValueEnd(), 2);
                double min = Math.min(start, end);
                double max = Math.max(start, end);
                yAxis.setExtremes(min, max);
                chart.drawChart();
                button.setEnabled(true);
            }
        });
    }

    private double around(double ch, int count) {
        double scale = Math.pow(10, count);
        return Math.round(ch * scale) / scale;
    }

    private Title createTitle() {
        Title title = new Title("Уровень SL");
        Style style = new Style();
        style.setColor(SolidColor.GREEN);
        title.setStyle(style);
        return title;
    }

    public void setSeriesMap(Map<SlaInterfaceI, List<SlaCountI>> map) {
        seriesMap.clear();
        int number = 0;
        for (Map.Entry<SlaInterfaceI, List<SlaCountI>> item : map.entrySet()) {
            SlaInterfaceI key = item.getKey();
            List<SlaCountI> value = item.getValue();

            DataSeries dataSeries = new DataSeries(key.getName());
            dataSeries.setVisible(false);
            PlotOptionsLine plot = new PlotOptionsLine();
            if (CollectionColors.COLORS.length > number) {
                plot.setColor(CollectionColors.COLORS[number++]);
            }

            plot.setShowInLegend(false);
            dataSeries.setVisible(false);
            dataSeries.setPlotOptions(plot);
            seriesMap.put(key, dataSeries);
            for (SlaCountI slaCount : value) {
                dataSeries.add(new DataSeriesItem(slaCount.getDateTime().toDate(), slaCount.getPercent()));
            }
        }

        Collection<DataSeries> values = seriesMap.values();
        Series[] array = values.toArray(new Series[values.size()]);
        chart.getConfiguration().setSeries(array);

        chart.drawChart();
    }

    @Override
    public void change(SlaInterfaceI slaInterface) {
        selectSla(slaInterface);
    }

    public void selectSla(SlaInterfaceI slaInterface) {
        DataSeries prevSeries = seriesMap.get(this.selectedSla);
        if (prevSeries != null) {
            prevSeries.setVisible(false);
        }
        DataSeries nowSeries = seriesMap.get(slaInterface);
        if (nowSeries != null) {
            nowSeries.setVisible(true);
        }
        chart.getConfiguration().getTitle().setText(TITLE + "<br>(" + slaInterface.getDescription() + ")");
        this.selectedSla = slaInterface;
        chart.drawChart();
    }

    public void resetZoom() {
        chart.getConfiguration().getyAxis().setExtremes(LIMIT_MIN, LIMIT_MAX);
        chart.drawChart();
    }

    public void setExtremes(SlaModelI model) {
        chart.setExtremes(model);
    }
}
