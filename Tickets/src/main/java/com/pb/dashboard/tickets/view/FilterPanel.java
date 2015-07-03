package com.pb.dashboard.tickets.view;

import com.pb.dashboard.core.model.Bank;
import com.pb.dashboard.core.model.Month;
import com.pb.dashboard.core.util.IntegerUtil;
import com.pb.dashboard.tickets.entype.TicketType;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.Calendar;

public class FilterPanel extends VerticalLayout {

    public static final Bank[] OUT_BANKS = {Bank.UKRAINE, Bank.A_BANK};
    public static final int START_YEAR = 2013;
    private static final String TYPE_TICKETS = "Вид билета";
    private static final String SELECT_BANK = "Выберите банк";
    private static final String SELECT_PERIOD = "Выберите период";
    private static final String APPLY = "Применить";

    private Filterable filterable;
    private VerticalLayout filter;
    private ComboBox typeSelect;
    private ComboBox bankSelect;
    private ComboBox yearSelect;
    private ComboBox monthSelect;

    public FilterPanel(final Filterable filterable) {
        this.filterable = filterable;
        setHeight("330px");
        setWidth("100%");

        initFilter();
        addComponent(filter);
        setExpandRatio(filter, 1);
    }

    private void initFilter() {
        filter = new VerticalLayout();
        filter.addStyleName("filter-main");
        filter.setSpacing(true);

        VerticalLayout typeBlock = createTypeBlock();
        VerticalLayout bankBlock = createBankBlock();
        VerticalLayout periodBlock = createPeriodBlock();
        Button applyButton = createApplyButton();

        filter.addComponent(typeBlock);
        filter.addComponent(bankBlock);
        filter.addComponent(periodBlock);
        filter.addComponent(applyButton);
        filter.addComponent(new Label(""));
    }

    private VerticalLayout createTypeBlock() {
        VerticalLayout ticketTypeSelectBlock = new VerticalLayout();
        ticketTypeSelectBlock.addComponent(new Label(TYPE_TICKETS));

        typeSelect = new ComboBox();
        for (TicketType type : TicketType.values()) {
            if (type == null) continue;
            typeSelect.addItem(type);
            typeSelect.setItemCaption(type, type.getName());
        }
        typeSelect.setNullSelectionAllowed(false);
        typeSelect.setValue(TicketType.ALL);
        typeSelect.setImmediate(true);

        ticketTypeSelectBlock.addComponent(typeSelect);
        return ticketTypeSelectBlock;
    }

    private VerticalLayout createBankBlock() {
        VerticalLayout bankTypeSelectBlock = new VerticalLayout();
        bankTypeSelectBlock.addComponent(new Label(SELECT_BANK));

        bankSelect = new ComboBox();
        for (Bank type : OUT_BANKS) {
            if (type == null) continue;
            bankSelect.addItem(type);
            bankSelect.setItemCaption(type, type.getName());
        }
        bankSelect.setNullSelectionAllowed(false);
        bankSelect.setValue(Bank.UKRAINE);
        bankSelect.setImmediate(true);

        bankTypeSelectBlock.addComponent(bankSelect);
        return bankTypeSelectBlock;
    }

    private VerticalLayout createPeriodBlock() {
        VerticalLayout periodSelectBlock = new VerticalLayout();
        periodSelectBlock.addComponent(new Label(SELECT_PERIOD));

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);

        yearSelect = new ComboBox();
        for (int i = START_YEAR; i <= year; i++) {
            yearSelect.addItem(String.valueOf(i));
        }
        yearSelect.setNullSelectionAllowed(false);
        yearSelect.setValue(String.valueOf(year));
        yearSelect.setImmediate(true);

        monthSelect = new ComboBox();
        for (Month type : Month.values()) {
            if (type == null) continue;
            monthSelect.addItem(type);
            monthSelect.setItemCaption(type, type.getName());
        }

        monthSelect.setNullSelectionAllowed(false);
        monthSelect.setValue(Month.YEAR);
        monthSelect.setImmediate(true);

        VerticalLayout periodSelect = new VerticalLayout();

        periodSelect.addComponent(yearSelect);
        periodSelect.addComponent(monthSelect);
        periodSelect.setSpacing(true);

        periodSelectBlock.addComponent(periodSelect);
        return periodSelectBlock;
    }

    private Button createApplyButton() {
        Button apply = new Button(APPLY);
        apply.addStyleName("apply");
        apply.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Bank bank = (Bank) bankSelect.getValue();
                TicketType type = (TicketType) typeSelect.getValue();
                Month month = (Month) monthSelect.getValue();

                int year = START_YEAR;
                String yearStr = String.valueOf(yearSelect.getValue());
                if (IntegerUtil.checkInt(yearStr)) {
                    year = Integer.parseInt(yearStr);
                }


                filterable.filterUpdated(type, bank, year, month);
            }
        });
        return apply;
    }
}