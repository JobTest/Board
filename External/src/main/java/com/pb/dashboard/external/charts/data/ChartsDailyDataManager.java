package com.pb.dashboard.external.charts.data;

import com.pb.dashboard.external.debtload.model.DebtLinesModel;
import com.pb.dashboard.external.debtload.repository.LoadItemsDao;
import com.pb.dashboard.external.debtload.repository.jdbc.DebtloadDbManager;
import com.vaadin.addon.charts.model.ListSeries;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class ChartsDailyDataManager {

    private static final Logger LOG = Logger.getLogger(ChartsDailyDataManager.class);
    private Date from;
    private Date to;
    private Collection<Collection<DebtLinesModel>> data = new ArrayList<Collection<DebtLinesModel>>();
    private LoadItemsDao dao = new DebtloadDbManager();
    private String[] categories;
    private String TITLE_Y = "Загрузки строк";
    private int bank;

    public ChartsDailyDataManager(int bank, Date from, Date to) {
        this.bank = bank;
        this.from = from;
        this.to = to;
        getData();
        for (Collection<DebtLinesModel> col : data) {
            for (DebtLinesModel dlm : col) {
                LOG.info(dlm.getLines() + "     " + dlm.getDate());
            }
        }
    }

    public void getData() {
        Date d = from;
        Calendar calendar;
        calendar = Calendar.getInstance();
        calendar.setTime(d);
        Calendar cal;
        cal = Calendar.getInstance();
        cal.setTime(to);
        cal.add(Calendar.DATE, 1);
        while (!calendar.getTime().equals(cal.getTime())) {
            String[] formatedDates = getFornatedDates(calendar.getTime(), calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            Collection<DebtLinesModel> arr = dao.getLinesModelByFromDateAndToDate(bank, formatedDates[0], formatedDates[1]);
            LOG.info(formatedDates[0] + "      " + formatedDates[1]);
            data.add(arr);
        }
    }

    private String[] getFornatedDates(Date from, Date to) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String end = "_23-59-59";
        String start = "_00-00-00";
        return new String[]{sdf.format(from) + start, sdf.format(to) + end};
    }

    public String[] getCategories() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);
        int k = 1;
        while (!calendar.getTime().equals(to)) {
            k++;
            calendar.add(Calendar.DATE, 1);
        }
        k++;
        categories = new String[k];
        calendar.setTime(from);
        for (int i = 0; i < categories.length; i++) {
            categories[i] = sdf.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        return categories;
    }

    public ListSeries getListSeriesByLines() {
        ListSeries ls = new ListSeries();
        ArrayList<Number> settedData = new ArrayList<Number>();
        for (Collection<DebtLinesModel> col : data) {
            int lines = 0;
            for (DebtLinesModel dlm : col) {
                lines += Integer.valueOf(dlm.getLines());
            }
            settedData.add(lines);
        }
        ls.setData(settedData);
        return ls;
    }

    public String getTitleY() {
        return TITLE_Y;
    }

    public ListSeries getListSeriesByFiles() {
        ListSeries ls = new ListSeries();
        ArrayList<Number> settedData = new ArrayList<Number>();
        for (Collection<DebtLinesModel> col : data) {
            int files = col.size();
            settedData.add(files);
        }
        ls.setData(settedData);
        return ls;
    }
}
