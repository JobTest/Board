package com.pb.dashboard.itdashbord.table.payment;

import com.pb.dashboard.itdashbord.db.ASEDBManager;
import com.vaadin.data.Item;
import com.vaadin.ui.Table;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CellRender implements Serializable {
    private static final String DATA = "Дата";
    private static final String DATA2 = "День недели";
    private static final String IN_TCO = "Кол-во платежей в ТСО";
    private static final String MY_PAY = "ТСО через меню \"Мои платежи\"";
    private String currentData;
    ASEDBManager mDb = ASEDBManager.getInstance();

    private static final long serialVersionUID = 9010301266327852669L;

    public Table.CellStyleGenerator cellEditing(final String dateToCompare, final TCOTableBean tcoTableBean, final Table tcoTable) {
        currentData = dateToCompare;
        mDb.clearMsg();
        final List<Object[]> props = tcoTableBean.getTcoHolder().get(dateToCompare);
        return new Table.CellStyleGenerator() {
            @Override
            public String getStyle(Table source, Object itemId, Object propertyId) {
                if (propertyId == null) {
                    return "white";
                }
                int row = ((Integer) itemId).intValue();
                int col = convertToInt((String) propertyId);

                Item item = source.getItem(itemId);
                String date = item.getItemProperty(DATA).getValue().toString();
                String dayOfWeek = item.getItemProperty(DATA2).getValue().toString();
                if (col == 3) {
                    Double valueFrom = getValueFrom(props, getMonthAgoDay(date), 2);
                    String inTco = item.getItemProperty(IN_TCO).getValue().toString();
                    if (valueFrom > Double.valueOf(inTco)) {
                        mDb.addMessage("Дата: " + date + " Значение:" + inTco + " " + "Дата: " + getMonthAgoDay(date) + "." + dateToCompare + " Значение:" + valueFrom);
                        if (dayOfWeek != null && (dayOfWeek.equals("сб") || dayOfWeek.equals("вс"))) {
                            return "redali";
                        }
                        return "red";
                    }
                }
                if (col == 4) {
                    Double valueFrom = getValueFrom(props, getMonthAgoDay(date), 3);
                    String myPayment = item.getItemProperty(MY_PAY).getValue().toString();
                    if (valueFrom > Double.valueOf(myPayment)) {
                        mDb.addMessage("Дата: " + date + " Значение:" + myPayment + " " + "Дата: " + getMonthAgoDay(date) + "." + dateToCompare + " Значение:" + valueFrom);
                        if (dayOfWeek != null && (dayOfWeek.equals("сб") || dayOfWeek.equals("вс"))) {
                            return "redali";
                        }
                        return "red";
                    }
                }
                if (dayOfWeek != null && (dayOfWeek.equals("сб") || dayOfWeek.equals("вс"))) {
                    if (col == 2) {
                        return "lava";
                    }
                    return "ali";
                }
                return "white";
            }

            private Double getValueFrom(List<Object[]> props, String day, int type) {
                Double value = 0d;
                for (int i = 0; i < props.size(); i++) {
                    String date = (String) props.get(i)[0];
                    if (getDay(day).equals(getDay(date))) {
                        value = Double.valueOf((String) props.get(i)[type]);
                    }
                }
                return value;
            }
        };
    }

    protected String getMonthAgoDay(String date) {
        try {
            Date curDay = new SimpleDateFormat("dd.MM.yyyy").parse(date);
            Calendar cal = Calendar.getInstance();
            int i = cal.get(Calendar.MONTH) + 1 - getMonth(currentData);
            cal.setTime(curDay);
            if (i > 1) {
                cal.add(Calendar.WEEK_OF_MONTH, -((4 * i) + 1));
            } else {
                cal.add(Calendar.WEEK_OF_MONTH, -4);
            }
            return String.valueOf(new SimpleDateFormat("dd").format(cal.getTime()));
        } catch (ParseException e) {
            return "0";
        }
    }

    private String getDay(String date) {
        String[] data = date.split("\\.");
        return data[0];
    }

    private int getMonth(String date) {
        String[] data = date.split("\\.");
        return Integer.valueOf(data[0]);
    }

    public int convertToInt(String item) {
        if (item.equals(DATA)) {
            return 1;
        }
        if (item.equals("День недели")) {
            return 2;
        }
        if (item.equals("Кол-во платежей в ТСО")) {
            return 3;
        }
        if (item.equals("ТСО через меню \"Мои платежи\"")) {
            return 4;
        }
        if (item.equals("%")) {
            return 5;
        }
        return 0;
    }

}
