package com.pb.dashboard.itdashbord.errors.errorSession.tableComponent;

import com.jensjansson.pagedtable.PagedTable;
import com.pb.dashboard.itdashbord.errors.db.container.ErrByCompany;
import com.pb.dashboard.itdashbord.errors.error.content.table.Pages;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class TableView extends VerticalLayout{

    private String ERR_CODE = "Код ошибки";
    private String ERRORS_NUMBER ="Кол-во";
    private String TYPE = "Тип ошибки";
    private String DECODE = "Расшифровка";
    private Pages pages;
    private PagedTable table;


    public TableView() {
        setSizeFull();
        initTable();
    }

    private void initTable() {
        table = new PagedTable();
        table.setSizeFull();
        table.setPageLength(5);

        table.addContainerProperty(ERR_CODE, Label.class, null);
        table.addContainerProperty(ERRORS_NUMBER, Integer.class, null);
        table.addContainerProperty(TYPE, Label.class, null);
        table.addContainerProperty(DECODE, Label.class, null);

//        table.setSortContainerPropertyId(ERRORS_NUMBER);
//        table.setSortAscending(false);

        table.setColumnAlignments(
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER
        );

        table.setImmediate(true);
        addComponent(table);
        addPages();
    }

    public void setData(List<ErrByCompany> data){
        int k = 0;
        for (ErrByCompany err : data){
            table.addItem(new Object[]{
                new Label(err.getErrorCode()),
                Integer.valueOf(err.getNumberOfErrors()),
                new Label(err.getErrorType()),
                new Label(err.getDecode())
            }, k);
            k++;
        }
        table.setSortContainerPropertyId(ERRORS_NUMBER);
        table.setSortAscending(false);
        removeComponent(pages);
        addPages();
    }

    public void addPages() {
        pages = new Pages(table);
        addComponent(pages);
        setComponentAlignment(pages, Alignment.TOP_RIGHT);
    }
}
