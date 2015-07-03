package com.pb.dashboard.itdashbord.errors.error.title.filter.Components;

import com.pb.dashboard.itdashbord.errors.ErrorsFiltersListener;
import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;

public class ComboBoxComponent extends ComboBox{
    private String[] BRANCH_ITEMS = {"Бердянский ф-л","Винницкий ф-л","Волынское ГРУ","Головной банк",
            "Днепропетровское РУ","Донецкий ф-л","Житомирское РУ","Закартпатское РУ","Запорожское РУ","Западное ГРУ",
            "Ивано-Франковский ф-л","КиевСити","Киевский ф-л","Киевский ф-л","Кировоградский ф-л","Краматорский ф-л",
            "Кременчугский ф-л","Криворожский ф-л","Луганский ф-л","Мариупольский ф-л","Николаевское РУ","Печерский ф-л",
            "Полтавское ГРУ","Ровенский ф-л","РЦ Киев","Столичный ф-л","Сумской ф-л","Тернопольский ф-л","Харьковское ГРУ",
            "Херсонский ф-л","Хмельницкий ф-л","Черкасский ф-л","Черниговское РУ","Черновицкий ф-л"};

    private String[] GROUP_ITEMS={"Невыясненные","Бюджетные","Коммерческие получатели","Коммунальные службы","Образование","Телекоммуникации","Служебные","Благотворительность"};

    private String[] STYPE_DEBT_ITEMS={"Online", "Off-line"};

    private ErrorsFiltersListener filter;

    public ComboBoxComponent(String caption, ErrorsFiltersListener filter) {
        super(caption);
        this.filter = filter;
        addItems(caption);
        setImmediate(true);
        }

    private void addItems(String caption) {
        if ("Филиал".equals(caption)){
            for (String BRANCH_ITEM : BRANCH_ITEMS) {
                addItem(BRANCH_ITEM);
                addListenerToBox(caption);
            }
        }
        if ("Группа вида".equals(caption)){
            for (String GROUP_ITEM : GROUP_ITEMS) {
                addItem(GROUP_ITEM);
                addListenerToBox(caption);
            }
        }
        if ("Вид задолженности".equals(caption)){
            for (String STYPE_DEBT_ITEM : STYPE_DEBT_ITEMS) {
                addItem(STYPE_DEBT_ITEM);
                addListenerToBox(caption);
            }
        }
    }

    private void addListenerToBox(final String caption) {
        addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if ("Филиал".equals(caption)){
                    filter.setBaranch((String) getValue());
                }
                if ("Группа вида".equals(caption)){
                    filter.setGroup((String) getValue());
                }
                if ("Вид задолженности".equals(caption)){
                    filter.setStypeDebt((String) getValue());
                }
            }
        });
    }


}
