package com.pb.dashboard.external.debtload.view.queue;

import com.pb.dashboard.core.model.Bank;
import com.pb.dashboard.core.model.Language;
import com.pb.dashboard.external.debtload.logic.DataProvider;
import com.pb.dashboard.external.debtload.view.table.QueueTableDataHolder;
import com.pb.dashboard.external.debtload.view.table.TableManager;
import com.vaadin.ui.*;

public class QueueViewPanelManager {

    private QueueView queueView;
    private Table queueTable;
    private Label rowsCnt;

    public QueueViewPanelManager(QueueView queueView) {
        this.queueView = queueView;
    }

    public Component getContentPanel() {
        QueueTableDataHolder dataHolder = getDataHolder();
        queueTable = TableManager.getInstance().buildQueueTable(dataHolder);

        VerticalLayout tablePanel = new VerticalLayout();
        tablePanel.setWidth("100%");

        Label infoLoad = new Label("* Для каталогов Load указывается количество строк файла");
        infoLoad.addStyleName("info");
        Label infoFtp = new Label("** Для каталогов FTP указывается количество файлов в каталоге ");
        infoFtp.addStyleName("info");
        VerticalLayout info = new VerticalLayout();
        info.addComponents(infoLoad, infoFtp);
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setSpacing(true);
        Label mDate = new Label("Время модификации: " + DataProvider.getInstance().getMDate());
        mDate.addStyleName("m-date");
        header.addComponents(info, mDate);
        header.setSpacing(true);


        HorizontalLayout total = new HorizontalLayout();
        total.setSpacing(true);
        rowsCnt = new Label();
        rowsCnt.addStyleName("total-label");
        rowsCnt.setValue(dataHolder.getRows().size() + "");
        total.addComponents(new Label("Всего на загрузку:"), rowsCnt);

        tablePanel.addComponents(header, queueTable, total);

        return tablePanel;
    }

    public void updateByCountry() {
        QueueTableDataHolder dataHolder = getDataHolder();
        TableManager.getInstance().fillTable(queueTable, dataHolder.getRows());
        rowsCnt.setValue(dataHolder.getRows().size() + "");
    }

    private QueueTableDataHolder getDataHolder() {
        Bank bank = (queueView.getLang() == Language.RU) ? Bank.RUSSIA : Bank.UKRAINE;
        QueueTableDataHolder dataHolder = new QueueTableDataHolder();
        dataHolder.load(DataProvider.getInstance().getQueueItemsByBank(bank));
        return dataHolder;
    }

}
