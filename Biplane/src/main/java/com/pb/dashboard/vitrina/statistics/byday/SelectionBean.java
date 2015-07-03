package com.pb.dashboard.vitrina.statistics.byday;

import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.core.config.ConfigManager;
import com.pb.dashboard.vitrina.core.config.information.QueryInf;
import com.pb.dashboard.vitrina.core.country.CountryGEO;
import com.pb.dashboard.vitrina.core.country.CountryI;
import com.pb.dashboard.vitrina.core.country.CountryRU;
import com.pb.dashboard.vitrina.core.country.CountryUA;
import com.pb.dashboard.vitrina.payment.data.StatEnum;
import com.pb.dashboard.vitrina.statistics.DataCollector;
import com.pb.dashboard.vitrina.statistics.TestDataHolder;
import com.pb.dashboard.vitrina.statistics.Utilities;
import com.pb.dashboard.vitrina.statistics.chart.ChartData;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SelectionBean implements Serializable {

    private static final long serialVersionUID = 8297606161642172504L;
    private StatEnum type;
    private List<DayHourMetrics> dayHourMetrics;
    private List<Object[]> tableData;
    private String total;

    private SelectionDate mainDate;
    private SelectionDate comparisonDate;

    private ASEDBManager dm = ASEDBManager.getInstance();
    protected CountryI country;
    protected ConfigManager manager;
    private ChartData chartData = new ChartData(this);

    private TestDataHolder testHolder = TestDataHolder.getInstance();

    public void setConfigManager(ConfigManager configManager) {
        manager = configManager;
    }

    public void collectByDate(Date date) {
        dayHourMetrics = generateData(date);
        //dayHourMetrics = randMainDate();                                            //test
        mainDate = buildSelectionDate(date, dayHourMetrics);
    }

    public void updateByType(StatEnum type) {
        this.type = type;
        mainDate.setByType(type);
        tableData = Utilities.convertToTableData(mainDate.getData());
        total = String.format("%,d", mainDate.getTotal());
        chartData.updateChartData(type);
    }

    public void collectByComparisonDate(Date date) {
        dayHourMetrics = generateData(date);
        //dayHourMetrics = randCompDate();                                          //test
        comparisonDate = buildSelectionDate(date, dayHourMetrics);
        comparisonDate.setByType(type);
        updateData();
    }

    public void compareByType(StatEnum type) {
        this.type = type;
        mainDate.setByType(type);
        comparisonDate.setByType(type);
        updateData();
    }

    public void updateData() {
        tableData = Utilities.convertToTableData(mainDate.getData(), comparisonDate.getData());
        total = Utilities.convertComparativeTotal(mainDate.getTotal(), comparisonDate.getTotal());
        chartData.updateCompChartData(type);
    }

    private SelectionDate buildSelectionDate(Date date, List<DayHourMetrics> metrics) {
        DataCollector collector = collectAllTypes(metrics);
        Map<StatEnum, List<Integer[]>> allTypes = (Map<StatEnum, List<Integer[]>>) collector.getData();
        Integer[] totalsByType = (Integer[]) collector.getTotal();
        return new SelectionDate(date, allTypes, totalsByType);
    }

    private DataCollector collectAllTypes(List<DayHourMetrics> dayHourMetrics) {

        int[] kassaTypes = {country.getKassaCash(), country.getKassaNonCash(), country.getKassaDebt(),
                country.getKassaFizLic(), country.getKassaUrLic()};
        int[] bassTypes = {country.getBassCash(), country.getBassNonCash(), country.getBassDebt(),
                country.getBassFizLic(), country.getBassUrLic()};
        int[] p24Types = {-1, country.getP24NonCash(), country.getP24Debt(), country.getP24FizLic(),
                country.getP24UrLic()};
        int[] l3700Types = {-1, country.get3700NonCash(), country.get3700Debt(), country.get3700FizLic(),
                country.get3700UrLic()};
        List<int[]> index = new ArrayList<int[]>(4);

        index.add(kassaTypes);
        index.add(bassTypes);
        index.add(p24Types);
        index.add(l3700Types);

        return Utilities.collectAllTypes(dayHourMetrics, index);
    }

    public List<Object[]> getTableData() {
        return tableData;
    }

    public String getSum() {
        return total;
    }

    public Date getDate() {
        return mainDate.getDate();
    }

    public List<Date> getDates() {
        List<Date> list = new ArrayList<Date>(2);
        list.add(mainDate.getDate());
        list.add(comparisonDate.getDate());
        return list;
    }

    public Map<StatEnum, List<Integer[]>> getAllTypes() {
        return mainDate.getAllTypes();
    }

    public Map<StatEnum, List<Integer[]>> getAllTypesToCompare() {
        return comparisonDate.getAllTypes();
    }

    public List<Integer[]> getMainData() {
        return mainDate.getData();
    }

    public List<Integer[]> getDataToCompare() {
        return comparisonDate.getData();
    }

    private int currentTime(Date date) {
        Locale locale = new Locale("ru");
        DateFormat df = new SimpleDateFormat("yyyyMMdd", locale);
        return Integer.parseInt(df.format(date));
    }

    public ChartData getChartDataRef() {
        return chartData;
    }

    private void reloadCountry() {
        switch (manager.getLang()) {
            case UA:
                country = new CountryUA();
                break;
            case RU:
                country = new CountryRU();
                break;
            case GEO:
                country = new CountryGEO();
                break;
            default:
                country = new CountryUA();
                break;
        }
    }

    private List<DayHourMetrics> generateData(Date date) {
        reloadCountry();
        return dm.getByDayMetrics(QueryInf.CURRENT_DAY_METRICS, currentTime(date));
    }

    private List<DayHourMetrics> randMainDate() {
        reloadCountry();
        return testHolder.getMainDate(country);
    }

    private List<DayHourMetrics> randCompDate() {
        return testHolder.getCompDate(country);
    }
}
