package com.pb.dashboard.itdashbord.table.payment;

import com.vaadin.data.Item;
import com.vaadin.ui.Table;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeekCellRender implements Serializable {
    private static final String DATA = "День недели";
    private static final String DATA2 = "Дата";

    private static final long serialVersionUID = 9010301266327852669L;

    public Table.CellStyleGenerator cellEditing() {
        return new Table.CellStyleGenerator() {
            @Override
            public String getStyle(Table source, Object itemId,
                                   Object propertyId) {
                if (propertyId == null) {
                    return "white";
                }
                int row = ((Integer) itemId).intValue();
                int col = convertToInt((String) propertyId);

                Item item = source.getItem(itemId);
                String dayOfWeek = item.getItemProperty(DATA).getValue().toString();
                if (dayOfWeek != null && (dayOfWeek.equals("сб") || dayOfWeek.equals("вс"))) {
                    if (col == 1 || col == 2) {
                        return "lava";
                    }
                    return "ali";
                }
                return "white";
            }

        };
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

    public Table.CellStyleGenerator cellEditingNoDayOfWeek() {
        return new Table.CellStyleGenerator() {
            @Override
            public String getStyle(Table source, Object itemId,
                                   Object propertyId) {
                if (propertyId == null) {
                    return "white";
                }
                int row = ((Integer) itemId).intValue();
                int col = convertToInt2((String) propertyId);

                Item item = source.getItem(itemId);
                String day = item.getItemProperty(DATA2).getValue().toString();
                if (isHolidays(day)) {
                    return "ali";
                }
                return "white";
            }

            private boolean isHolidays(String day) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                    calendar.setTime(sdf.parse(day));
                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                            calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        return true;
                    }
                    return false;
                } catch (ParseException e) {
                    return false;
                }
            }

        };
    }

    public int convertToInt2(String item) {
        if (item.equals("Дата")) {
            return 1;
        }
        if (item.equals("Операций в ТСО")) {
            return 2;
        }
        if (item.equals("Авторизация по QR-коду")) {
            return 3;
        }
        if (item.equals("Без авторизации")) {
            return 4;
        }
        if (item.equals("%")) {
            return 5;
        }
        if (item.equals("Авторизация по карте")) {
            return 6;
        }
        if (item.equals("Авторизация по телефону")) {
            return 7;
        }
        if (item.equals("телефон,%")) {
            return 8;
        }
        return 0;
    }
}
