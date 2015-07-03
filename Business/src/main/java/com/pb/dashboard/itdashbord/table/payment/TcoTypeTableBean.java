package com.pb.dashboard.itdashbord.table.payment;

import com.pb.dashboard.itdashbord.db.DataHolder;

import java.util.ArrayList;
import java.util.List;

public class TcoTypeTableBean extends TableBeanAbs {
    private String[] columns = {"Сегмент", "август", "Июль", "Июнь", "Май"};
    private String aug = "";
    private String jul = "";
    private String jun = "";
    private String may = "";


    public List<Object[]> getPayments() {
        aug = "0";
        jul = "0";
        jun = "0";
        may = "0";
        List<Object[]> data = new ArrayList<Object[]>();
        Object[] holder = DataHolder.getPaymentByType();
        for (int i = 0; i < holder.length; i++) {
            Object[] item = (Object[]) holder[i];
            data.add(new Object[]{item[0], item[1], item[2], item[3], item[4]});
            aug = multyply(aug, item[1].toString());
            jul = multyply(jul, item[2].toString());
            jun = multyply(jun, item[3].toString());
            may = multyply(may, item[4].toString());
        }
        return data;
    }

    public Object[] getSum() {
        return new Object[]{"Всего", pbformat.format(Double.valueOf(aug)), pbformat.format(Double.valueOf(jul)), pbformat.format(Double.valueOf(jun)),
                pbformat.format(Double.valueOf(may))};
    }

    public String[] getColumns() {
        return columns;
    }
}
