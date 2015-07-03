package com.pb.dashboard.vitrina.selection;

import com.pb.dashboard.vitrina.core.config.ConfigManager;
import com.pb.dashboard.vitrina.core.types.BiplaneChannel;
import com.pb.dashboard.vitrina.payment.data.StatEnum;
import com.pb.dashboard.vitrina.statistics.byday.SelectionBean;
import com.pb.dashboard.vitrina.statistics.chart.ChartData;
import com.vaadin.ui.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SelectionManager implements Serializable {
    private SelectionBean statsDayBean = new SelectionBean();
    private ComparativeCellRenderer cellRenderer = new ComparativeCellRenderer();
    private ChartData chartData = statsDayBean.getChartDataRef();

    public void compareByType(StatEnum stats) {
        cellRenderer.reset();
        statsDayBean.compareByType(stats);
        cellRenderer.setDiscrepancy(statsDayBean.getMainData(), statsDayBean.getDataToCompare());
    }

    public void collectData(Date date) {
        statsDayBean.collectByDate(date);
    }

    public void updateByType(StatEnum type) {
        cellRenderer.reset();
        statsDayBean.updateByType(type);
    }

    public void collectComparisonData(Date date) {
        statsDayBean.collectByComparisonDate(date);
        cellRenderer.setDiscrepancy(statsDayBean.getMainData(), statsDayBean.getDataToCompare());
    }

    public List<Object[]> getPayments() {
        return statsDayBean.getTableData();
    }

    public String getTotal() {
        return statsDayBean.getSum();
    }

    public List<List<Number>> getRegularChartData() {
        return chartData.getRegularChartData();
    }

    public List<List<Number>> getRegChartComparisonData() {
        return chartData.getComparisonChartData();
    }

    public List<Number> getMainDateTotals() {
        return chartData.getMainDateTotals();
    }

    public List<Number> getCompDateTotals() {
        return chartData.getCompareDateTotals();
    }

    public List<Date> getComparisonDates() {
        return statsDayBean.getDates();
    }

    public Table.CellStyleGenerator getCellStyleGenerator() {
        return cellRenderer.getGenerator();
    }

    public Date getDate() {
        return statsDayBean.getDate();
    }

    public void setConfigManager(ConfigManager manager) {
        statsDayBean.setConfigManager(manager);
    }

    public StatEnum convertEnum(BiplaneChannel channel) {
        if (channel == BiplaneChannel.PAYDESK) return StatEnum.KASSA;
        if (channel == BiplaneChannel.P24) return StatEnum.P24;
        if (channel == BiplaneChannel.LINE3700) return StatEnum.P3700;
        if (channel == BiplaneChannel.TERMINAL) return StatEnum.BASS;
        if (channel == BiplaneChannel.ALL) return StatEnum.ALL;
        return StatEnum.UNDEF;
    }

}
