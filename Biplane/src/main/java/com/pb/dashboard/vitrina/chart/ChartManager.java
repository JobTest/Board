package com.pb.dashboard.vitrina.chart;

import com.pb.dashboard.vitrina.chart.data.ChartMData;
import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.core.config.information.MetricsInf;

import java.text.DateFormatSymbols;
import java.util.*;

public class ChartManager {

    private static final long serialVersionUID = -36619534382577896L;

    private List<ChartMData> chartMDataByPeriod;
    private List<ChartMData> CashKassaData;
    private List<ChartMData> NoCashKassData;
    private List<ChartMData> DebtKassaData;
    private List<ChartMData> CashBassData;
    private List<ChartMData> NoCashBassData;
    private List<ChartMData> DebtBassData;
    private List<ChartMData> NoCashP24Data;
    private List<ChartMData> DebtP24Data;
    private List<ChartMData> chartCurMonth;
    private List<ChartMData> chartPreMonth;
    private List<ChartMData> chartPrePreMonth;
    public List<Integer> vList;
    ASEDBManager asedbm = ASEDBManager.getInstance();

    public ChartManager() {
        updateData();
    }

    public void updateData() {
        init();
        fillData();
        collectData();
    }

    private void init() {
        CashKassaData = new ArrayList<ChartMData>();
        NoCashKassData = new ArrayList<ChartMData>();
        DebtKassaData = new ArrayList<ChartMData>();
        CashBassData = new ArrayList<ChartMData>();
        NoCashBassData = new ArrayList<ChartMData>();
        DebtBassData = new ArrayList<ChartMData>();
        NoCashP24Data = new ArrayList<ChartMData>();
        DebtP24Data = new ArrayList<ChartMData>();
        vList = new ArrayList<Integer>();
    }

    private void fillData() {
        asedbm.updateData();
        chartMDataByPeriod = asedbm.chartMDataByPeriod;
    }

    private void collectData() {
        for (ChartMData chartData : chartMDataByPeriod) {
            switch (chartData.getPkey()) {
                case MetricsInf.KASSA_CASH_METRIC:
                    CashKassaData.add(chartData);
                    break;
                case MetricsInf.KASSA_NOCASH_METRIC:
                    NoCashKassData.add(chartData);
                    break;
                case MetricsInf.KASSA_DEBT_METRIC:
                    DebtKassaData.add(chartData);
                    break;
                case MetricsInf.BASS_CASH_METRIC:
                    CashBassData.add(chartData);
                    break;
                case MetricsInf.BASS_NOCASH_METRIC:
                    NoCashBassData.add(chartData);
                    break;
                case MetricsInf.BASS_DEBT_METRIC:
                    DebtBassData.add(chartData);
                    break;
                case MetricsInf.P24_NOCASH_METRIC:
                    NoCashP24Data.add(chartData);
                    break;
                case MetricsInf.P24_DEBT_METRIC:
                    DebtP24Data.add(chartData);
                    break;
                default:
                    break;
            }
        }
    }

    private void sumMetrics(List<ChartMData> t1, List<ChartMData> t2) {
        List<ChartMData> Data = new ArrayList<ChartMData>();
        for (ChartMData ct1 : t1) {
            for (ChartMData ct2 : t2) {
                if (ct1.getDate().equals(ct2.getDate())) {
                    Data.add(new ChartMData(0, ct1.getValue() + ct2.getValue(), ct1.getDate()));
                }
            }
        }
        generateData(Data);
    }

    private void generateData(List<ChartMData> Data) {
        chartCurMonth = new ArrayList<ChartMData>();
        chartPreMonth = new ArrayList<ChartMData>();
        chartPrePreMonth = new ArrayList<ChartMData>();

        vList.clear();
        for (ChartMData c1 : Data) {
            if (parseDate(c1.getDate()) == (getCurrentMonth(0))) {
                chartCurMonth.add(new ChartMData(Integer.parseInt(c1.getDate().substring(6, 8)), c1.getValue(), c1.getDate()));
                vList.add(c1.getValue());
            } else if (parseDate(c1.getDate()) == (getCurrentMonth(1))) {
                chartPreMonth.add(new ChartMData(Integer.parseInt(c1.getDate().substring(6, 8)), c1.getValue(), c1.getDate()));
                vList.add(c1.getValue());
            } else if (parseDate(c1.getDate()) == (getCurrentMonth(2))) {
                chartPrePreMonth.add(new ChartMData(Integer.parseInt(c1.getDate().substring(6, 8)), c1.getValue(), c1.getDate()));
                vList.add(c1.getValue());
            }
        }
        if (vList.isEmpty()) {
            vList.add(0);
        }
    }

    private Integer[] createSeries(List<ChartMData> temp) throws NumberFormatException {
        Integer[] data = new Integer[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            data[temp.get(i).getPkey() - 1] = temp.get(i).getValue();
        }
        return data;
    }

    private int parseDate(String date) {
        return Integer.parseInt(date.substring(4, 6));
    }

    private int getCurrentMonth(int type) {
        Calendar cal = Calendar.getInstance();
        if (type == 0) {
            return cal.get(Calendar.MONTH) + 1;
        } else if (type == 1) {
            return cal.get(Calendar.MONTH);
        } else if (type == 2) {
            return cal.get(Calendar.MONTH) - 1;
        }
        return cal.get(Calendar.MONTH) + 1;
    }

    public Map<String, Integer[]> getCurrentMonthValue() {
        Map<String, Integer[]> map = new HashMap<String, Integer[]>();
        map.put(formatMonth(chartCurMonth.get(0).getDate()),
                createSeries(chartCurMonth));
        return map;
    }

    public Map<String, Integer[]> getAgotMonthValue() {
        Map<String, Integer[]> map = new HashMap<String, Integer[]>();
        map.put(formatMonth(chartPreMonth.get(0).getDate()),
                createSeries(chartPreMonth));
        return map;
    }

    public Map<String, Integer[]> get2AgoMonthValue() {
        Map<String, Integer[]> map = new HashMap<String, Integer[]>();
        map.put(formatMonth(chartPrePreMonth.get(0).getDate()),
                createSeries(chartPrePreMonth));
        return map;
    }

    public void getKassaPayment() {
        updateData();
        sumMetrics(CashKassaData, NoCashKassData);
    }

    public void getBaassPayment() {
        updateData();
        sumMetrics(CashBassData, NoCashBassData);
    }

    public void getP24Payment() {
        updateData();
        generateData(NoCashP24Data);
    }

    public String formatMonth(String month) {
        DateFormatSymbols symbols = new DateFormatSymbols(new Locale("ru"));
        String[] monthNames = symbols.getMonths();
        return monthNames[Integer.parseInt(month.substring(4, 6)) - 1];
    }
}
