package com.pb.dashboard.monitoring.timings.sla;

import com.pb.dashboard.core.util.IntegerUtil;
import com.pb.dashboard.dao.entity.vitrinametrics.SlaCountI;
import com.pb.dashboard.dao.entity.vitrinametrics.SlaInterfaceI;
import com.pb.dashboard.dao.entity.vitrinametrics.SlaTimingI;
import com.pb.dashboard.dao.service.MonitoringServiceI;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.monitoring.components.filter.FilterModelI;
import com.pb.dashboard.monitoring.components.navigator.ContentItem;
import com.pb.dashboard.monitoring.timings.navigator.NavigatorItem;
import com.pb.dashboard.monitoring.timings.sla.builderchart.SlaDecorator;
import com.pb.dashboard.monitoring.timings.tabsheet.ChartDetail;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 06.11.14_14:59
 */
public class SlaController implements SlaControllerI {

    private SlaModelI model;
    private SlaView view;

    private SlaDecorator slaDecorator = new SlaDecorator();

    private ChartDetail chartDetail;

    public SlaController() {
        this.model = new SlaModel();
        view = new SlaView(this, model);
        view.setDecorator(slaDecorator);
    }

    private void update() {
        if (!model.isValidInputDate()) {
            return;
        }
        updateSlaData();
        updateSlaInterfaces();
        updateSlaParent();
        changeOrderSlaCounts();

        slaDecorator.getPercentChart().setSeriesMap(model.getSlaCounts());
        slaDecorator.getCountsChart().setSeriesMap(model.getSlaCounts());
        slaDecorator.getTimingsChart().setTimingsBySlaInterface(model.getSlaTimings());


        if (checkAllData()) {
            SlaInterfaceI firstValue = model.getSlaCounts().keySet().iterator().next();
            slaDecorator.setSelectedSla(firstValue);
            view.setVisible(true);
        } else {
            view.setVisible(false);
        }
    }

    private void updateSlaInterfaces() {
        MonitoringServiceI timings = ServiceFactory.getMonitoring();
        Map<Integer, SlaInterfaceI> slaInterfaces = timings.getSlaInterfaces(model.getPkeyInterface(), MonitoringServiceI.Parent.ALL);
        model.setSlaInterfaces(slaInterfaces);
    }

    private void updateSlaParent() {
        Map<Integer, SlaInterfaceI> parents = ServiceFactory.getMonitoring().getSlaInterfaces(model.getPkeyInterface(), MonitoringServiceI.Parent.PARENT);
        if (!parents.isEmpty()) {
            SlaInterfaceI parent = getFirstValue(parents);
            model.setParent(parent);
        }
    }

    private void updateSlaData() {
        MonitoringServiceI timings = ServiceFactory.getMonitoring();
        Map<SlaInterfaceI, List<SlaCountI>> slaCounts;
        Map<SlaInterfaceI, List<SlaTimingI>> slaTimings;
        switch (model.getFilterRange()) {
            case R10MIN:
                slaCounts = timings.getSlaCountsByHp(model.getPkeyInterface(), model.getDate(), MonitoringServiceI.Parent.ALL);
                slaTimings = timings.getSlaTimingsByHp(model.getPkeyInterface(), model.getDate(), MonitoringServiceI.Parent.ALL);
                break;
            case HOUR:
                slaCounts = timings.getSlaCountsByHour(model.getPkeyInterface(), model.getDate(), MonitoringServiceI.Parent.ALL);
                slaTimings = timings.getSlaTimingsByHour(model.getPkeyInterface(), model.getDate(), MonitoringServiceI.Parent.ALL);
                break;
            default:
                slaCounts = timings.getSlaCountsByDays(model.getPkeyInterface(), model.getFromDate(), model.getToDate(), MonitoringServiceI.Parent.ALL);
                slaTimings = timings.getSlaTimingsByDays(model.getPkeyInterface(), model.getFromDate(), model.getToDate(), MonitoringServiceI.Parent.ALL);
        }
        model.setSlaCounts(slaCounts);
        model.setSlaTimings(slaTimings);
    }

    private <K, V> V getFirstValue(Map<K, V> map) {
        return map.values().iterator().next();
    }

    //parent slaInterface will be last
    private void changeOrderSlaCounts() {
        if (model.getParent() == null) {
            return;
        }
        Map<SlaInterfaceI, List<SlaCountI>> map = model.getSlaCounts();
        SlaInterfaceI parent = model.getParent();
        Map<SlaInterfaceI, List<SlaCountI>> res = new LinkedHashMap<>();
        for (Map.Entry<SlaInterfaceI, List<SlaCountI>> item : map.entrySet()) {
            SlaInterfaceI slaInterface = item.getKey();
            List<SlaCountI> slaCountIs = item.getValue();
            if (!slaInterface.equals(parent)) {
                res.put(slaInterface, slaCountIs);
            }
        }
        List<SlaCountI> slaCountIs = map.get(parent);
        if (slaCountIs != null) {
            res.put(parent, slaCountIs);
        }
        model.setSlaCounts(res);
    }

    private boolean checkAllData() {
        if (model.getSlaInterfaces().isEmpty()) {
            return false;
        }
        if (model.getParent() == null) {
            return false;
        }
        if (model.getSlaCounts().isEmpty() || model.getSlaTimings().isEmpty()) {
            return false;
        }
        return true;
    }

    public void setItem(int navigatorId, ContentItem item) {
        if (NavigatorItem.INTERFACE.getPkey() != navigatorId || !IntegerUtil.checkInt(item.getPkey())) {
            return;
        }
        Integer pkeyInterface = Integer.valueOf(item.getPkey());
        model.setPkeyInterface(pkeyInterface);
        update();
    }

    public void modified(FilterModelI filter) {
        this.model.setFilterRange(filter.getFilterRange());
        switch (filter.getFilterRange()) {
            case R10MIN:
            case HOUR:
                model.setDate(filter.getDate());
                break;
            case DAY:
                model.setFromDate(filter.getDateFrom());
                model.setToDate(filter.getDateTo());
                chartDetail.setDefault(filter.getDateFrom(), filter.getDateTo());
        }
        model.setReglament(filter.isReglament());
        slaDecorator.getPercentChart().setExtremes(model);
        slaDecorator.getCountsChart().setExtremes(model);
        slaDecorator.getTimingsChart().setExtremes(model);
        update();
    }

    public SlaView getView() {
        return view;
    }

    @Override
    public void setChartDetail(ChartDetail chartDetail) {
        this.chartDetail = chartDetail;
        slaDecorator.setChartDetail(chartDetail);
    }

    @Override
    public SlaModelI getModel() {
        return model;
    }
}
