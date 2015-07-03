package com.pb.dashboard.monitoring.timings.charts;

import com.pb.dashboard.monitoring.context.ContextFactory;
import com.pb.dashboard.monitoring.timings.chooser.ChooserItem;
import com.pb.dashboard.monitoring.timings.sla.SlaController;
import com.pb.dashboard.monitoring.timings.sla.SlaControllerI;
import com.pb.dashboard.monitoring.timings.tabsheet.ChartDetail;
import com.pb.dashboard.monitoring.timings.tabsheet.IndicatorsModelI;
import com.pb.dashboard.monitoring.timings.transferlink.LinkManagerI;
import com.vaadin.addon.charts.PointClickEvent;
import com.vaadin.addon.charts.model.Series;

/**
 * Created by vlad
 * Date: 12.12.14_15:08
 */
public class ChartsController implements ChartsControllerI {

    private ChartsModelI model;
    private ChartsView view;

    private SlaControllerI slaController;
    private ChartDetail chartDetail;

    public ChartsController(ChartsModelI model) {
        this.model = model;
        view = createChartsView();

        slaController = new SlaController();
        model.setSlaModel(slaController.getModel());
    }

    private ChartsView createChartsView() {
        return ContextFactory.getContext().getBean(ChartsView.class, this, model);
    }

    @Override
    public SlaControllerI getSlaController() {
        return slaController;
    }

    @Override
    public ChartsView getView() {
        return view;
    }

    @Override
    public ChartsModelI getModel() {
        return model;
    }

    @Override
    public void setLinkManager(LinkManagerI linkManager) {
        chartDetail = new ChartDetail(linkManager);
        slaController.setChartDetail(chartDetail);
    }

    @Override
    public void setChooserItem(ChooserItem item) {
        model.setLogarithmic(item == ChooserItem.LOGARITHMIC);
        model.notifyAllObservers();
    }

    @Override
    public void resetZoom() {
        view.updateInterval(model);
        view.redrawCharts();
    }

    @Override
    public void pointClick(PointClickEvent event) {
        if (chartDetail != null) {
            Series series = event.getSeries();
            String category = event.getCategory();
            chartDetail.updatePeriod(series, category);
            String interfaceName = event.getSeries().getName();
            int x = event.getAbsoluteX();
            int y = event.getAbsoluteY();
            chartDetail.showPopup(interfaceName, x, y);
        }
    }

    @Override
    public void selectionChart(Double start, Double end) {
        double max = Math.max(start, end);
        double min = Math.min(start, end);
        view.setExtremes(min, max);
    }

    @Override
    public void modified(IndicatorsModelI indicatorsModel) {
        model.setIndicatorsModel(indicatorsModel);
        model.notifyAllObservers();
    }
}
