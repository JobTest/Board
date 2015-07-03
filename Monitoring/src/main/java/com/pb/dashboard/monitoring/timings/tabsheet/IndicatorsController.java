package com.pb.dashboard.monitoring.timings.tabsheet;

import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceLimitI;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import com.pb.dashboard.monitoring.components.filter.FilterModelI;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.pb.dashboard.monitoring.components.navigator.ContentItem;
import com.pb.dashboard.monitoring.timings.charts.ChartsController;
import com.pb.dashboard.monitoring.timings.charts.ChartsControllerI;
import com.pb.dashboard.monitoring.timings.charts.ChartsModel;
import com.pb.dashboard.monitoring.timings.charts.ChartsModelI;
import com.pb.dashboard.monitoring.timings.transferlink.LinkManagerI;

import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 09.12.14_15:05
 */
public class IndicatorsController implements IndicatorsControllerI {

    private IndicatorsTabSheet view;
    private IndicatorsModelI model;

    private ChartsControllerI chartsController;

    public IndicatorsController(IndicatorsModelI model) {
        this.model = model;
        init();
        view = new IndicatorsTabSheet(this, model);
        view.setSlaView(chartsController.getSlaController().getView());
    }

    private void init() {
        initCharts();
    }

    private void initCharts() {
        ChartsModelI chartsModel = new ChartsModel();
        chartsController = new ChartsController(chartsModel);
        model.addObserver(chartsController);
    }

    @Override
    public IndicatorsTabSheet getView() {
        return view;
    }

    @Override
    public ChartsControllerI getChartsController() {
        return chartsController;
    }

    @Override
    public void setLinkManager(LinkManagerI linkManager) {
        chartsController.setLinkManager(linkManager);
    }

    @Override
    public void setTimingMetricsMap(Map<InterfaceMetricI, List<MetricItem>> filterItems) {
        for (List<MetricItem> metricItems : filterItems.values()) {
            if (metricItems.isEmpty()) {
                metricItems.add(new MetricItem());
            }
        }
        model.setTimingMetricsMap(filterItems);
    }

    @Override
    public void setFilterRange(FilterRange filterRange) {
        model.setFilterRange(filterRange);
    }

    @Override
    public void setData(Map<InterfaceMetricI, List<MetricItem>> filterItems, FilterRange range) {
        setTimingMetricsMap(filterItems);
        setFilterRange(range);
        update();
    }

    @Override
    public void update() {
        model.update();
    }

    @Override
    public void setLimit(InterfaceLimitI limit) {
        if (limit != null) {
            model.setLimit(limit);
        }
    }

    @Override
    public void setItem(int navigatorId, ContentItem item) {
        chartsController.getSlaController().setItem(navigatorId, item);
    }

    @Override
    public void modified(FilterModelI obj) {
        chartsController.getSlaController().modified(obj);
    }
}
