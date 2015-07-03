package com.pb.dashboard.tester.testing;

import java.util.ArrayList;

public class TesterController {

    private static final int countWorkingDaysPrevWeek = UtilsCalendar.getCountWorkingDaysPrevWeek();
    private static final int countWorkingDaysPresWeek = UtilsCalendar.getCountWorkingDaysPresWeek();
    private static final int countWorkingDaysPrevMonth = UtilsCalendar.getCountWorkingDaysPrevMonth();
    private static final int countWorkingDaysPresMonth = UtilsCalendar.getCountWorkingDaysPresMonth();
    private static final int countWorkingDaysPrevQuarter = UtilsCalendar.getCountWorkingDaysPrevQuarter();
    private static final int countWorkingDaysPresQuarter = UtilsCalendar.getCountWorkingDaysPresQuarter();
    private TesterDBManager manager;
    private TesterPanel panel;

    public TesterController() {
        init();
    }

    private void init() {
        manager = new TesterDBManager();
        ArrayList<TestsTesters> testsTesterses = manager.getTestsTesterses();

        panel = new TesterPanel();
        panel.initLayout(testsTesterses.size());

        for (TestsTesters test : testsTesterses) {
            ArrayList<Cell> list = new ArrayList<Cell>();
            list.add(new Cell(test.getName(), ""));

//
            Cell week1 = getWeek(test.getCountAutoTestWeekPrev(), test.getCountAutoTestWeekNow());
            Cell month1 = getMonth(test.getCountAutoTestMonthPrev(), test.getCountAutoTestMonthNow());
            Cell quarter1 = getQuarter(test.getCountAutoTestQuarterPrev(), test.getCountAutoTestQuarterNow());
//            Map.Entry<String, String> half_year1 = ;
            list.add(week1);
            list.add(month1);
            list.add(quarter1);
            list.add(new Cell("", ""));
//
            Cell week2 = getWeek(test.getCountManualTestWeekPrev(), test.getCountManualTestWeekNow());
            Cell month2 = getMonth(test.getCountManualTestMonthPrev(), test.getCountManualTestMonthNow());
            Cell quarter2 = getQuarter(test.getCountManualTestQuarterPrev(), test.getCountManualTestQuarterNow());
//            Map.Entry<String, String>  half_year2 = "";
            list.add(week2);
            list.add(month2);
            list.add(quarter2);
            list.add(new Cell("", ""));
//
//            String week3 = getWeek(test.getCountManualTestWeekPrev(), test.getCountManualTestWeekNow());
//            String month3 = getMonth(test.getCountManualTestMonthPrev(), test.getCountManualTestMonthNow());
//            String quarter3 = getQuarter(test.getCountManualTestQuarterPrev(), test.getCountManualTestQuarterNow());
//            String half_year3 = "";
//            list.add(week3);
//            list.add(month3);
//            list.add(quarter3);
//            list.add(half_year3);

            panel.addProject(list);
        }
    }

    private Cell getWeek(int prev, int pres) {
        return getValue(prev, pres, countWorkingDaysPrevWeek, countWorkingDaysPresWeek, "на прошлой неделе/на этой неделе/в неделю");
    }

    private Cell getMonth(int prev, int pres) {
        return getValue(prev, pres, countWorkingDaysPrevMonth, countWorkingDaysPresMonth, "на прошлом месяце/в этом месяце/в месяц");
    }

    private Cell getQuarter(int prev, int pres) {
        return getValue(prev, pres, countWorkingDaysPrevQuarter, countWorkingDaysPresQuarter, "на прошлом квартале/на этом квартале/в квартал");
    }

    private Cell getValue(int prev, int pres, int workingDaysPrev, int workingDaysPres, String ends) {
        String res = prev + "/" + pres + "<br>";
        double avgPrev = (double) prev / workingDaysPrev;
        double avgPres = (double) pres / workingDaysPres;

        int perce = 0;
        int avgCount = (int) Math.round(avgPres - avgPrev);
        if (avgPrev != 0) {
            perce = (int) Math.round(avgPres / avgPrev * 100);
        }
        res += avgCount + "/" + perce + "%";

        res = addColor(res, avgCount);
        String desc = getDescription(new int[]{prev, pres, avgCount, perce}, ends);
        return new Cell(res, desc);
    }

    private String addColor(String text, int count) {
        if (count < 0) {
            return "<span style='color:red' >" + text + "</span>";
        }
        if (count > 0) {
            return "<span style='color:green' >" + text + "</span>";
        }
        return text;
    }

    private String getDescription(int[] ch, String ends) {
        String[] end = ends.split("/");
        return ch[0] + " - количество тестов " + end[0] + "<br>" +
                ch[1] + " - количество тестов " + end[1] + "<br>" +
                ch[2] + " - среднее количество тестов " + end[2] + "<br>" +
                ch[3] + "% - процентное соотношение изменений среднего значения за день<br>";
    }

    public TesterPanel getPanel() {
        return panel;
    }
}