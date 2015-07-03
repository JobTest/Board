package com.pb.dashboard.external.debtload.view.table;

import com.pb.dashboard.external.debtload.model.DebtQueueModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueueTableDataHolder {

    public static final String[] COLUMN_NAMES = { "ID получателя", "Наименование получателя", "Дата файла", "LOAD: Full", "LOAD: Part",
            "LOAD: Multi", "LOAD: MultiPart", "LOAD: Test", "FTP: Full", "FTP: Part", "FTP: Multi", "FTP: Test", "Филиал" };

    private List<Object[]> rows;

    public List<Object[]> getRows() {
        return rows;
    }

    public void load(Collection<DebtQueueModel> models) {
        rows = new ArrayList<Object[]>();
        for (DebtQueueModel model : models) {
            Object[] row = new Object[COLUMN_NAMES.length];
            row[0] = String.valueOf(model.getRecipientId());
            row[1] = model.getRecipientName();
            row[2] = model.getFileDate();
            row[3] = String.valueOf(model.getLoadFull());
            row[4] = String.valueOf(model.getLoadPart());
            row[5] = String.valueOf(model.getLoadMulti());
            row[6] = String.valueOf(model.getLoadMultiPart());
            row[7] = String.valueOf(model.getLoadTest());
            row[8] = String.valueOf(model.getFtpFull());
            row[9] = String.valueOf(model.getFtpPart());
            row[10] = String.valueOf(model.getFtpMulti());
            row[11] = String.valueOf(model.getFtpTest());
            row[12] = model.getBranch();
            rows.add(row);
        }
    }
}
