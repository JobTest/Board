package com.pb.dashboard.monitoring.timings.charts;

import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.timings.chooser.ChooserItem;
import com.pb.dashboard.monitoring.timings.sla.SlaControllerI;
import com.pb.dashboard.monitoring.timings.tabsheet.IndicatorsModelI;
import com.pb.dashboard.monitoring.timings.transferlink.LinkManagerI;
import com.vaadin.addon.charts.PointClickEvent;

/**
 * Created by vlad
 * Date: 12.12.14_15:09
 */
public interface ChartsControllerI extends Observer<IndicatorsModelI> {

    SlaControllerI getSlaController();

    ChartsView getView();

    ChartsModelI getModel();

    void setLinkManager(LinkManagerI linkManager);

    void setChooserItem(ChooserItem item);

    void resetZoom();

    void pointClick(PointClickEvent event);

    void selectionChart(Double selectionStart, Double selectionEnd);
}
