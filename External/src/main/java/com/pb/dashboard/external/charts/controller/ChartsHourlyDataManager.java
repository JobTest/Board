package com.pb.dashboard.external.charts.controller;

import com.pb.dashboard.external.charts.data.DailyChartCollum;
import com.pb.dashboard.external.debtload.model.DebtLinesModel;
import com.pb.dashboard.external.debtload.repository.LoadItemsDao;
import com.pb.dashboard.external.debtload.repository.jdbc.DebtloadDbManager;
import com.vaadin.addon.charts.model.ListSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ChartsHourlyDataManager {
    private Date date;
    private String[] categories;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String TITLE_Y ="Загрузки строк";
    private Collection<DebtLinesModel> data;
    private int bank;
    private LoadItemsDao dao = new DebtloadDbManager();

    public ChartsHourlyDataManager(int bank, Date date) {
        this.date = date;
        this.bank = bank;
        getData();
    }

    public String[] getCategories() {
        SimpleDateFormat sdfhour = new SimpleDateFormat("H");
        if (sdf.format(date).equals(sdf.format(new Date()))){
            int currentDateLenght = Integer.valueOf(sdfhour.format(new Date()));
            categories = new String[currentDateLenght+1];
            for (int i = 0; i < currentDateLenght+1; i++) {
                categories[i] ="";
                if(i<10){
                    categories[i]+="0";
                }
                categories[i] += String.valueOf(i)+":00";
            }
        }
        else {
            categories = new String[24];
            for (int i = 0; i < 24; i++) {
                categories[i] ="";
                if(i<10){
                    categories[i]+="0";
                }
                categories[i] += String.valueOf(i)+":00";
            }
        }
        return categories;
    }

    public String getTitleY() {
        return TITLE_Y;
    }

    public ListSeries getListSeriesByLines() {
        ListSeries ls = new ListSeries();
        DailyChartCollum[] collums = new DailyChartCollum[categories.length];
        for (int i = 0; i < categories.length; i++) {
            collums[i] = new DailyChartCollum(categories[i].split(":")[0]);
        }
        for (DebtLinesModel dlm: data){
            for (DailyChartCollum collum : collums) {
                if (collum.getKey().equals(dlm.getDate().split(":")[0])) {
                    collum.setLines(Integer.valueOf(dlm.getLines()));
                }
            }
        }
        ArrayList settedData = new ArrayList();
        for (DailyChartCollum collum : collums) {
            settedData.add(collum.getLines());
        }

        ls.setData(settedData);
        return ls;
    }
    private void getData(){
        String[] formatedDates = getFornatedDates();
        data = dao.getLinesModelByFromDateAndToDate(bank, formatedDates[0], formatedDates[1]);
    }

    private String[] getFornatedDates() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String end = "_23-59-59";
        String start = "_00-00-00";
        return new String[]{sdf.format(date)+start,sdf.format(date)+end};
    }

    public ListSeries  getListSeriesByFiles() {
        ListSeries ls = new ListSeries();
        DailyChartCollum[] collums = new DailyChartCollum[categories.length];
        for (int i = 0; i < categories.length; i++) {
            collums[i] = new DailyChartCollum(categories[i].split(":")[0]);
        }
        for (DebtLinesModel dlm: data){
            for (DailyChartCollum collum : collums) {
                if (collum.getKey().equals(dlm.getDate().split(":")[0])) {
                    collum.setFiles(1);
                }
            }
        }
        ArrayList settedData = new ArrayList();
        for (DailyChartCollum collum : collums) {
            settedData.add(collum.getFiles());
        }
        ls.setData(settedData);
        return ls;
    }
}
