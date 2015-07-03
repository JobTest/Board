package com.pb.dashboard.external.debtload.view.load;

import com.pb.dashboard.core.model.Language;
import com.pb.dashboard.external.debtload.logic.DataProvider;
import com.pb.dashboard.external.debtload.view.table.LoadViewDataHolder;
import com.pb.dashboard.external.debtload.view.table.TableManager;
import com.vaadin.ui.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoadTablePanel {

    private TotalsPanel totals = new TotalsPanel();
    private TotalsStringsPanel totalsStr = new TotalsStringsPanel();
    private Table table = TableManager.getInstance().buildLoadTable();
    private LoadViewDataHolder dataHolder;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private boolean isManualLoad;
    private Date date;

    public void setDate(Date date) {
        this.date = date;
    }

    public LoadTablePanel(boolean isManualLoad) {
        this.isManualLoad = isManualLoad;
    }

    public Component getView() {
        VerticalLayout view = new VerticalLayout();
        view.setSizeFull();
        String caption = (isManualLoad) ? "Нерегламентная загрузка" : "Регламент";

        VerticalLayout total = new VerticalLayout();
        total.addComponent(totals);
        total.addComponent(totalsStr);

        HorizontalLayout bar = new HorizontalLayout();
        bar.addComponent(total);
        bar.setSizeFull();
        view.addComponents(new Label(caption), bar, table);
        return view;
    }

    public void loadData(Language lang, Date date) {
        dataHolder = DataProvider.getInstance().getLoadTableDataHolder(lang, sdf.format(date), isManualLoad);
    }

    public boolean isEmpty() {
        return dataHolder.getTableRows().size() == 0;
    }

    public void updateDisplay() {
        TableManager.getInstance().fillTable(table, dataHolder.getTableRows());
        totals.setData(dataHolder.getLoadsCnt(), dataHolder.getDiffLoadsCnt(), dataHolder.getErrLoadsCnt(), dataHolder.getPercentLoadsCnt());
        totalsStr.setData(dataHolder.getLoadsCntStr(), dataHolder.getErrLoadsCntStr(), dataHolder.getPercentLoadsCntStr());
    }

    public String getmDate() {
        return dataHolder.getmDate();
    }

}
