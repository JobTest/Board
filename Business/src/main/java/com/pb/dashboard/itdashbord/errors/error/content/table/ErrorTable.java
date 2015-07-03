package com.pb.dashboard.itdashbord.errors.error.content.table;

import com.jensjansson.pagedtable.PagedTable;
import com.pb.dashboard.core.hierarchy.Dashboard;
import com.pb.dashboard.itdashbord.errors.ErrorsFiltersListener;
import com.pb.dashboard.itdashbord.errors.db.container.ErrorsData;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;

import java.util.List;

public class ErrorTable extends VerticalLayout {

    public static final String ID = "ID";
    public static final String NAME = "Наименование";
    public static final String BRANCH = "Филиал";
    public static final String OKPO = "ОКПО";
    public static final String VIEW = "Вид";
    public static final String RECIPIENT_TYPE = "Тип получателей";
    public static final String GROUP_TYPE = "Группа вида";
    public static final String COUNT_ERROR = "Кол-во ошибок";
    public static final String TYPE = "Вид задолженности";

    private PagedTable table;
    private Pages pages;

    public PagedTable getTable() {
        return table;
    }

    public ErrorTable() {
        setSizeFull();
        initTable();
    }

    private void initTable() {
        table = new PagedTable();
        table.setSizeFull();
        table.setPageLength(10);

        table.addContainerProperty(ID, Link.class, null);
        table.addContainerProperty(NAME, Label.class, null);
        table.addContainerProperty(BRANCH, Label.class, null);
        table.addContainerProperty(OKPO, Label.class, null);
        table.addContainerProperty(VIEW, Label.class, null);
        table.addContainerProperty(RECIPIENT_TYPE, Label.class, null);
        table.addContainerProperty(GROUP_TYPE, Label.class, null);
        table.addContainerProperty(COUNT_ERROR, Integer.class, null);
        table.addContainerProperty(TYPE, Label.class, null);

        table.setColumnAlignments(
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER
        );
        table.setImmediate(true);
        addComponent(table);
        addPages();
    }

    public void addPages() {
        pages = new Pages(table);
        addComponent(pages);
        setComponentAlignment(pages, Alignment.TOP_RIGHT);
    }

    public void setData(List<ErrorsData> datas, final ErrorsFiltersListener filter) {
        int k = 0;//счетчик ctrok по другому не віходило
        for (final ErrorsData data : datas) {
            Button link = new Button(data.getAsArray()[0]);
            link.setStyleName(Reindeer.BUTTON_LINK);
            link.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    UI.getCurrent().getNavigator().navigateTo(getAdress(data, filter));
                }
            });
            table.addItem(new Object[]{link,
                    new Label(data.getAsArray()[1]),
                    new Label(data.getAsArray()[2]),
                    new Label(data.getAsArray()[3]),
                    new Label(data.getAsArray()[4]),
                    new Label(data.getAsArray()[5]),
                    new Label(data.getAsArray()[6]),
                    Integer.valueOf(data.getAsArray()[7]),
                    new Label(data.getAsArray()[8]),
            }, k);
            k++;
        }
        table.setSortContainerPropertyId(COUNT_ERROR);
        table.setSortAscending(false);
        removeComponent(pages);
        addPages();
    }

    public String getAdress(ErrorsData data, ErrorsFiltersListener filter) {
        String adress = Dashboard.Templates.Sessions.PATH + "/" + data.getId() + "/";
        String[] splitedDateFrom = filter.getDateFrom().split(" ");
        for (String aSplitedDateFrom : splitedDateFrom) {
            adress += aSplitedDateFrom + "/";
        }
        String[] splitedDateTo = filter.getDateTo().split(" ");
        for (String aSplitedDateTo : splitedDateTo) {
            adress += aSplitedDateTo + "/";
        }
        return adress;
    }
}
