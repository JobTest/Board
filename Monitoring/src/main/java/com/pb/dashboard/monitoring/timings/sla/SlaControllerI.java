package com.pb.dashboard.monitoring.timings.sla;

import com.pb.dashboard.monitoring.components.filter.FilterModelI;
import com.pb.dashboard.monitoring.components.navigator.ContentItem;
import com.pb.dashboard.monitoring.timings.tabsheet.ChartDetail;

/**
 * Created by vlad
 * Date: 06.11.14_15:03
 */
public interface SlaControllerI {

    public void setItem(int navigatorId, ContentItem item);

    public void modified(FilterModelI model);

    public SlaView getView();

    void setChartDetail(ChartDetail chartDetail);

    SlaModelI getModel();

}
