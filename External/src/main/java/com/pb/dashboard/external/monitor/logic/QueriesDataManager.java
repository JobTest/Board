package com.pb.dashboard.external.monitor.logic;

import com.pb.dashboard.dao.entity.biplanesupport.db.InterfaceData;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.external.monitor.chart.ChartData;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Series;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by vlad
 * Date: 29.10.14_14:17
 */
public class QueriesDataManager {

    public final static String[] TEML_INTERFACES = {"b_create", "b_get", "getLight", "b_findByPay", "find", "findAll"};
    public final static String[] PAY_INTERFACES = {"searchAD", "selectPAByT", "create", "save2b",
            "confirm", "saveAsT", "searchDByT", "createGroupRegular", "printReceipts", "getHints"};

    private final static String[] QUERIES_CHART_CATEGORIES = {"ОК", "4хх статус", "5хх статус"};
    private List<ChartData> queriesViewChartData = new ArrayList<ChartData>();

    public QueriesDataManager() {
        loadQueriesViewData();
    }

    public void loadQueriesViewData() {
        Map<String, List<InterfaceData>> paymentsQueriesByHour = ServiceFactory.getExternal().getRecStatsByHour(PAY_INTERFACES);
        Map<String, List<InterfaceData>> temlQueriesByHour = ServiceFactory.getExternal().getTempStatsByHour(TEML_INTERFACES);
        queriesViewChartData.addAll(getQueriesChartData(paymentsQueriesByHour, PAY_INTERFACES));
        queriesViewChartData.addAll(getQueriesChartData(temlQueriesByHour, TEML_INTERFACES));
    }

    public List<ChartData> getPaymentsQueries() {
        return queriesViewChartData.subList(0, PAY_INTERFACES.length);
    }

    public List<ChartData> getTemplQueries() {
        return queriesViewChartData.subList(PAY_INTERFACES.length, queriesViewChartData.size());
    }

    private List<ChartData> getQueriesChartData(Map<String, List<InterfaceData>> dbResult, String[] interfaces) {
        Set<String> set = dbResult.keySet();
        String[] periods = new String[set.size()];
        periods = set.toArray(periods);

        List<ChartData> chartDataList = new ArrayList<ChartData>();
        for (int i = 0; i < interfaces.length; i++) {
            List<Series> seriesList = new ArrayList<Series>();
            List<Number> cnt = new ArrayList<Number>();
            List<Number> cnt400 = new ArrayList<Number>();
            List<Number> cnt500 = new ArrayList<Number>();
            for (String period : periods) {
                List<InterfaceData> list = dbResult.get(period);
                InterfaceData interfeceData = list.get(i);
                cnt.add(interfeceData.getCount());
                cnt400.add(interfeceData.getErr400count());
                cnt500.add(interfeceData.getErr500count());
            }
            for (int j = 0; j < QUERIES_CHART_CATEGORIES.length; j++) {
                ListSeries series = new ListSeries(QUERIES_CHART_CATEGORIES[j]);
                if (j == 0) series.setData(cnt);
                else if (j == 1) series.setData(cnt400);
                else series.setData(cnt500);
                seriesList.add(series);
            }
            chartDataList.add(new ChartData(periods, seriesList));
        }
        return chartDataList;
    }
}