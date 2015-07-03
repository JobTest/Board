package com.pb.dashboard.itdashbord.table.authorize;

import com.pb.dashboard.itdashbord.table.payment.TableBeanAbs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AuthorizePhoneBean extends TableBeanAbs implements Serializable {
    private static final long serialVersionUID = 52762365167122532L;
    private Map<String, List<Object[]>> tcoHolder = new LinkedHashMap<String, List<Object[]>>();
    private Object[] holder;
    private String date = null;
    private String moreOne = "0";
    private String moreTree = "0";
    private String moreFive = "0";
    private String moreTen = "0";

    public AuthorizePhoneBean(Object[] holder) {
        super();
        this.holder = holder;
        populatePayments();
    }

    public void setHolder(Object[] holder) {
        this.holder = holder;
    }

    private void populatePayments() {
        List<Object[]> payments = new ArrayList<Object[]>();
        for (int i = 0; i < holder.length; i++) {
            Object[] item = (Object[]) holder[i];
            String dateTemp = createStamp(item[0].toString());
            if (date == null) {
                date = dateTemp;
            }
            if (dateTemp.equals(date)) {
                payments.add(new Object[]{item[0].toString(), item[1].toString(), item[2].toString(),
                        item[3].toString(), item[4].toString(), item[5].toString(),
                        item[6].toString(), item[7].toString()});
                continue;
            } else {
                tcoHolder.put(date, payments);
                date = dateTemp;
                payments = new ArrayList<Object[]>();
            }
        }
        tcoHolder.put(date, payments);
    }


    public List<Object[]> getPayments() {
        clearData();
        List<Object[]> payments = new ArrayList<Object[]>();
        for (int i = 0; i < holder.length; i++) {
            Object[] item = (Object[]) holder[i];
            payments.add(new Object[]{item[0].toString(), item[8].toString(), item[9].toString(),
                    item[10].toString(), item[11].toString()});
            moreOne = multyply(moreOne, item[8].toString());
            moreTree = multyply(moreTree, item[9].toString());
            moreFive = multyply(moreFive, item[10].toString());
            moreTen = multyply(moreTen, item[11].toString());
        }
        return payments;
    }

    public Object[] getSumAll() {
        return new Object[]{"Всего", pbformat.format(Double.valueOf(moreOne)), pbformat.format(Double.valueOf(moreTree)),
                pbformat.format(Double.valueOf(moreFive)), pbformat.format(Double.valueOf(moreTen))};
    }

    private String createStamp(String date) {
        String[] data = date.split("\\.");
        return data[1] + "." + data[2];
    }

    private void clearData() {
        moreOne = "0";
        moreTree = "0";
        moreFive = "0";
        moreTen = "0";
    }

}
