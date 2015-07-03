package com.pb.dashboard.itdashbord.db;

import com.vaadin.server.VaadinService;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class DataHolder {

    private final static Logger LOG = Logger.getLogger(DataHolder.class);
    private static BusinessDBManager dbManager = new BusinessDBManager();

    public static Object[] getTcoPayments() {
        String path = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + File.separator + "WEB-INF" + File.separator + "payments.txt";
        Scanner s;
        try {
            s = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            LOG.error("File not found - " + path);
            return new Object[0][0];
        }
        ArrayList<String[]> records = new ArrayList<String[]>();
        String[] daysOfWeek = new String[]{"вс", "пн", "вт", "ср", "чт", "пт", "сб"};
        while (s.hasNext()) {
            try {
                String[] record = new String[4];
                for (int i = 0; i < 3; i++) {
                    record[i] = s.next();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(record[0]));
                String day = daysOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1];
                record[3] = day;
                records.add(record);
            } catch (Exception e) {
            }
        }
        return records.toArray(new Object[records.size()]);
    }

    public static Object[] getPaymentByType() {
        return new Object[]{
                new Object[]{"Облэнерго", "32180", "11786", "2623", "1316"},
                new Object[]{"Стационарная телефония", "23020", "10172", "2357", "613"},
                new Object[]{"Интернет", "25663", "8150", "1461", "446"},
                new Object[]{"Газ", "14654", "5784", "516", "829"},
                new Object[]{"Водоканал и канализация", "9220", "4314", "1430", "780"},
                new Object[]{"ЖЭКИ (квартплата)", "9616", "3813", "1028", "553"},
                new Object[]{"ИРЦ", "8608", "3206", "767", "468"},
                new Object[]{"Комерческие платежи", "10406", "2227", "1300", "405"},
                new Object[]{"Тепло", "4070", "2007", "602", "348"},
                new Object[]{"Кабельное/спутниковое ТВ", "5263", "1673", "304", "119"},
                new Object[]{"Мусор, экология", "3908", "1646", "319", "120"},
                new Object[]{"Сетевые компании(метод прямых продаж)", "6674", "938", "94", "53"},
                new Object[]{"Домофоны", "990", "905", "71", "21"},
                new Object[]{"Прочие...", "2366", "883", "110", "52"},
                new Object[]{"Страховые компании", "6440", "780", "71", "52"},
                new Object[]{"Детские сады", "3719", "500", "206", "185"},
                new Object[]{"Лифтовое хозяство", "802", "436", "58", "13"},
                new Object[]{"Погашение кредитов", "1584", "242", "51", "41"},
                new Object[]{"Мобильная телефония", "425", "217", "31", "3"},
                new Object[]{"Вузы и бизнес школы", "1281", "183", "97", "57"},
                new Object[]{"Строит.компании (окна, двери и прочее)", "827", "136", "22", "2"},
                new Object[]{"УГСО(правление государственной службы охраны при У", "295", "70", "15", "5"},
                new Object[]{"Печатные издания, реклама, СМИ", "191", "68", "16", "12"},
                new Object[]{"Медуслуги, спортивные клубы,...", "296", "51", "23", "19"},
                new Object[]{"Прочее (курсы, ...)", "171", "36", "22", "23"},
                new Object[]{"Школы", "114", "35", "19", "14"},
                new Object[]{"Частные охранные агентства, сигнализации", "152", "30", "13", "4"},
                new Object[]{"Налоги", "56", "21", "9", "4"},
                new Object[]{"СТО", "102", "20", "4", "2"},
                new Object[]{"Паспортные столы, ОВИРы", "50", "17", "1", "1"},
                new Object[]{"Лицеи, коледжи", "96", "13", "10", "4"},
                new Object[]{"Недвижимость", "19", "12", "6", "0"},
                new Object[]{"Туристические компании", "116", "10", "0", "0"},
                new Object[]{"Сетевые оптово-розничные предприятия(продукты пита", "182", "9", "1", "4"},
                new Object[]{"Бездоговорные", "42", "6", "6", "1"},
                new Object[]{"Техникумы", "74", "6", "3", "6"},
                new Object[]{"Рыночный сбор, местный налог", "34", "3", "1", "2"},
                new Object[]{"ГИСы", "11", "2", "0", "1"},
                new Object[]{"Госреестр", "47", "2", "1", "2"},
                new Object[]{"Строительные склады", "15", "2", "0", "0"},
                new Object[]{"АЗС", "1", "1", "0", "0"},
                new Object[]{"БТИ", "13", "1", "0", "0"},
                new Object[]{"Бюджет (бездоговорной)", "5", "1", "1", "1"},
                new Object[]{"Коллекторские агентства", "1", "1", "0", "1"},
                new Object[]{"Нотариат", "4", "1", "0", "0"},
                new Object[]{"Услуги разрешительной системы при УМВД", "29", "1", "0", "0"}};
    }

    public static Object[] getAuthorizationData() {
        return dbManager.getAuthorizationData();
    }
}
