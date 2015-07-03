package com.pb.dashboard.vitrina.statistics.chart;

import com.pb.dashboard.vitrina.core.types.Period;
import com.pb.dashboard.vitrina.payment.data.StatEnum;
import com.pb.dashboard.vitrina.statistics.Utilities;
import com.pb.dashboard.vitrina.statistics.byday.SelectionBean;

import java.util.ArrayList;
import java.util.List;

public class ChartData {

    private final int PERIODS = Period.values().length;

    private List<Number> totalPaymentsMainDate;
    private List<Number> totalPaymentsDateToCompare;

    private List<Number> cashData;
    private List<Number> nonCashData;
    private List<Number> debtData;

    private ChartDataHolder mainHolder;
    private ChartDataHolder compHolder;

    private SelectionBean dayStats;

    public ChartData(SelectionBean dayStats) {
        this.dayStats = dayStats;
    }

    public void updateChartData(StatEnum type) {
        cashData = new ArrayList<Number>();
        nonCashData = new ArrayList<Number>();
        debtData = new ArrayList<Number>();

        mainHolder = new ChartDataHolder();

        if (type == StatEnum.ALL) {
            int[] cash = new int[PERIODS],
                    noCash = new int[PERIODS],
                    debt = new int[PERIODS];
            for (StatEnum currentType : Utilities.types) {
                List<Integer[]> list = dayStats.getAllTypes().get(currentType);
                for (int i = 0; i < PERIODS; i++) {
                    Integer[] arr = list.get(i);
                    cash[i] += arr[1];
                    noCash[i] += arr[2];
                    debt[i] += arr[3];
                    mainHolder.addPayments(arr[0]);
                    mainHolder.addCash(arr[1]);
                    mainHolder.addNoCash(arr[2]);
                    mainHolder.addDebt(arr[3]);
                    mainHolder.addPhys(arr[4]);
                    mainHolder.addJur(arr[5]);
                }
            }
            for (int i = 0; i < PERIODS; i++) {
                cashData.add(cash[i]);
                nonCashData.add(noCash[i]);
                debtData.add(debt[i]);
            }
        } else {
            List<Integer[]> list = dayStats.getAllTypes().get(type);
            for (int i = 0; i < Period.values().length; i++) {
                Integer[] arr = list.get(i);
                cashData.add(arr[1]);
                nonCashData.add(arr[2]);
                debtData.add(arr[3]);
                mainHolder.addPayments(arr[0]);
                mainHolder.addCash(arr[1]);
                mainHolder.addNoCash(arr[2]);
                mainHolder.addDebt(arr[3]);
                mainHolder.addPhys(arr[4]);
                mainHolder.addJur(arr[5]);
            }
        }
    }


    public void updateCompChartData(StatEnum type) {
        totalPaymentsMainDate = new ArrayList<Number>();
        totalPaymentsDateToCompare = new ArrayList<Number>();

        mainHolder = new ChartDataHolder();
        compHolder = new ChartDataHolder();

        if (type == StatEnum.ALL) {
            int[] mainDate = new int[PERIODS],
                    dateToCompare = new int[PERIODS];
            for (StatEnum currentType : Utilities.types) {
                List<Integer[]> mainDateList = dayStats.getAllTypes().get(currentType);
                List<Integer[]> compDateList = dayStats.getAllTypesToCompare().get(currentType);
                for (int i = 0; i < PERIODS; i++) {
                    Integer[] arrM = mainDateList.get(i);
                    Integer[] arrC = compDateList.get(i);
                    mainDate[i] += arrM[0];
                    dateToCompare[i] += arrC[0];
                    mainHolder.addPayments(arrM[0]);
                    compHolder.addPayments(arrC[0]);
                    mainHolder.addCash(arrM[1]);
                    compHolder.addCash(arrC[1]);
                    mainHolder.addNoCash(arrM[2]);
                    compHolder.addNoCash(arrC[2]);
                    mainHolder.addDebt(arrM[3]);
                    compHolder.addDebt(arrC[3]);
                    mainHolder.addPhys(arrM[4]);
                    compHolder.addPhys(arrC[4]);
                    mainHolder.addJur(arrM[5]);
                    compHolder.addJur(arrC[5]);
                }
            }
            for (int i = 0; i < PERIODS; i++) {
                totalPaymentsMainDate.add(mainDate[i]);
                totalPaymentsDateToCompare.add(dateToCompare[i]);
            }
        } else {
            List<Integer[]> mainDateList = dayStats.getAllTypes().get(type);
            List<Integer[]> compDateList = dayStats.getAllTypesToCompare().get(type);
            for (int i = 0; i < Period.values().length; i++) {
                Integer[] arrM = mainDateList.get(i);
                Integer[] arrC = compDateList.get(i);
                totalPaymentsMainDate.add(arrM[0]);
                totalPaymentsDateToCompare.add(arrC[0]);
                mainHolder.addPayments(arrM[0]);
                compHolder.addPayments(arrC[0]);
                mainHolder.addCash(arrM[1]);
                compHolder.addCash(arrC[1]);
                mainHolder.addNoCash(arrM[2]);
                compHolder.addNoCash(arrC[2]);
                mainHolder.addDebt(arrM[3]);
                compHolder.addDebt(arrC[3]);
                mainHolder.addPhys(arrM[4]);
                compHolder.addPhys(arrC[4]);
                mainHolder.addJur(arrM[5]);
                compHolder.addJur(arrC[5]);
            }
        }
    }

    public List<List<Number>> getRegularChartData() {
        List<List<Number>> data = new ArrayList<List<Number>>(3);
        data.add(cashData);
        data.add(nonCashData);
        data.add(debtData);
        return data;
    }

    public List<List<Number>> getComparisonChartData() {
        List<List<Number>> data = new ArrayList<List<Number>>(2);
        data.add(totalPaymentsMainDate);
        data.add(totalPaymentsDateToCompare);
        return data;
    }

    public List<Number> getMainDateTotals() {
        List<Number> list = new ArrayList<Number>(6);
        list.add(mainHolder.getPayments());
        list.add(mainHolder.getCash());
        list.add(mainHolder.getNoCash());
        list.add(mainHolder.getPhys());
        list.add(mainHolder.getJur());
        list.add(mainHolder.getDebt());
        return list;
    }

    public List<Number> getCompareDateTotals() {
        List<Number> list = new ArrayList<Number>(6);
        list.add(compHolder.getPayments());
        list.add(compHolder.getCash());
        list.add(compHolder.getNoCash());
        list.add(compHolder.getPhys());
        list.add(compHolder.getJur());
        list.add(compHolder.getDebt());
        return list;
    }
}
