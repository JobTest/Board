package com.pb.dashboard.quality.view.tabsheet;

import com.pb.dashboard.quality.chart.ChartDataHolder;
import com.pb.dashboard.quality.view.table.RPTableDataHolder;

import java.util.List;

public class RPTabsheetDataHolder {

    private List<ChartDataHolder> chartPercentage;
    private List<ChartDataHolder> chartQuantity;
    private List<RPTableDataHolder> RPTableDataHolder;

    public RPTabsheetDataHolder(List<ChartDataHolder> chartPercentage, List<ChartDataHolder> chartQuantity,
                                List<RPTableDataHolder> RPTableDataHolder) {
        this.chartPercentage = chartPercentage;
        this.chartQuantity = chartQuantity;
        this.RPTableDataHolder = RPTableDataHolder;
    }

    public List<ChartDataHolder> getChartPercentage() {
        return chartPercentage;
    }

    public List<ChartDataHolder> getChartQuantity() {
        return chartQuantity;
    }

    public List<RPTableDataHolder> getRPTableDataHolder() {
        return RPTableDataHolder;
    }
}
