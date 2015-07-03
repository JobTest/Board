package com.pb.dashboard.external.debtload.view.table;

import com.pb.dashboard.external.debtload.model.DebtLoadModel;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LoadViewDataHolder {

    public static final String[] COLUMN_NAMES = { "ID получателя", "Наименование получателя", "Начало загрузки", "Конец загрузки",
            "Время загрузки", "Загружено", "В разногласия", "Тип", "Протокол расхождений", "Текст ошибки", "Филиал" };

    private List<Object[]> tableRows;
    private String mDate; // modification date (when automated tool updated DB data)
    private String lDate; // load date (date chosen by user for display)
    private int loadsCnt = 0; // total loaded items
    private int diffLoadsCnt = 0; // items loaded with differences
    private int errLoadsCnt = 0; // items loaded with errors
    private int loadsCntStr = 0; // total loaded strings
    private int errLoadsCntStr = 0; // strings loaded with differences
    private double percentLoadsCnt = 0;
    private double percentLoadsCntStr = 0;

    public List<Object[]> getTableRows() {
        return tableRows;
    }

    public String getmDate() {
        return mDate;
    }

    public String getlDate() {
        return lDate;
    }

    public int getLoadsCnt() {
        return loadsCnt;
    }

    public int getDiffLoadsCnt() {
        return diffLoadsCnt;
    }

    public int getErrLoadsCnt() {
        return errLoadsCnt;
    }

    public int getLoadsCntStr() {
        return loadsCntStr;
    }

    public int getErrLoadsCntStr() {
        return errLoadsCntStr;
    }

    public double getPercentLoadsCnt() {
        return percentLoadsCnt;
    }

    public double getPercentLoadsCntStr() {
        return percentLoadsCntStr;
    }

    public void load(Collection<DebtLoadModel> models, String lDate, String mDate) {
        this.lDate = lDate;
        this.mDate = mDate;
        tableRows = new ArrayList<Object[]>();
        for (DebtLoadModel model : models) {
            Object[] row = new Object[COLUMN_NAMES.length];
            row[0] = String.valueOf(model.getRecipientId());
            row[1] = model.getRecipientName();
            row[2] = model.getLoadStartTime();
            row[3] = model.getLoadEndTime();
            row[4] = getDownloadTime(model.getLoadEndTime(), model.getLoadStartTime());
            row[5] = model.getLinesLoaded();
            row[6] = String.valueOf(model.getDiff());
            row[7] = model.getType();
            row[8] = (!model.getDiffLink().equals("")) ? new Link("Файл", new ExternalResource(model.getDiffLink())) : null;
            row[9] = model.getErrorText();
            row[10] = model.getBranch();
            loadsCntStr += model.getLinesLoaded();
            tableRows.add(row);
            if (model.getDiff() > 0) {
                diffLoadsCnt++;
                errLoadsCntStr += Integer.valueOf(String.valueOf(model.getDiff()));
            }
            if (!model.getErrorText().equals("Нет")) errLoadsCnt++;
            loadsCnt++;
        }
        setPercents();
    }

    private String getDownloadTime(String loadEndTime, String loadStartTime) {
        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setDecimalSeparator('.');
        DecimalFormat f = new DecimalFormat("#,##0.00", s);

        String[] resalt = new String[3];

        String[] end = loadEndTime.split(":");
        String[] start = loadStartTime.split(":");

        double endSecond = Double.valueOf(end[2]);
        double startSecond = Double.valueOf(start[2]);
        int endMin = Integer.valueOf(end[1]);
        int startMin = Integer.valueOf(start[1]);
        int endHour = Integer.valueOf(end[0]);
        int startHour = Integer.valueOf(start[0]);
        for (int i = 0; i < resalt.length; i++) {
            resalt[i] = "";
        }
        if (endSecond > startSecond){
            double resSecond = endSecond-startSecond;
            if (resSecond < 10){resalt[2] = "0";}
            resalt[2] += f.format(resSecond);
        }
        else {
            double resSecond = 60.0 - (startSecond - endSecond);
            if (resSecond < 10){resalt[2] = "0";}
            resalt[2] += f.format(resSecond);
            endMin--;
        }
        if (endMin >= startMin){
            if (endMin - startMin < 10){resalt[1] += "0";}
            resalt[1] += String.valueOf(endMin - startMin);
        }
        else{
            if (60 - (startMin - endMin) < 10){resalt[1] += "0";}
            resalt[1] += String.valueOf(60 - (startMin - endMin));
            endHour--;
        }
        if (endHour >= startHour){
            resalt[0] = String.valueOf(endHour - startHour);
        }
        else {
            resalt[0] = String.valueOf(24 - (startHour - endHour));
        }
        String res = "";
        for (int i = 0; i < resalt.length-1; i++) {
            res += resalt[i]+":";
        }
        res += resalt[resalt.length-1];
        return res;
    }

    private void setPercents() {
        percentLoadsCnt = getPercent(loadsCnt,diffLoadsCnt,errLoadsCnt);
        percentLoadsCntStr = getPercent(loadsCntStr,errLoadsCntStr);
    }

    private double getPercent(int loaded, int diff, int err){
        if (diff + err == 0){
            return 100.0;
        }
        else{
            double  result =100.0-100*((double)diff+(double)err)/(double)loaded;
            return Math.rint(100.0*result)/100.0;
        }
    }

    private double getPercent(int loaded, int err){
        if (err == 0){
            return 100.0;
        }
        else{
            double  result =100.0-100*((double)err)/(double)loaded;
            return Math.rint(100.0*result)/100.0;
        }
    }
}
