package com.pb.dashboard.tickets.view.table;

import com.pb.dashboard.tickets.entype.TicketType;

import java.math.BigDecimal;
import java.util.*;

public class TableDataHolder {

    private Object[] footer;
    private List<Object[]> rows;

    public TableDataHolder() {
        setEmpty();
    }

    public TableDataHolder(Map<TicketType, BigDecimal[]> dbResult) {
        setData(dbResult);
    }

    public Object[] getFooter() {
        return footer;
    }

    public List<Object[]> getRows() {
        return rows;
    }

    private void setData(Map<TicketType, BigDecimal[]> dbResult) {
        int columnsCnt = 4; // total table columns
        BigDecimal[] footerVals = new BigDecimal[columnsCnt];
        for (int i = 0; i < footerVals.length; i++) {
            footerVals[i] = new BigDecimal(0);
        }
        String intFormat = "%,d";
        String doubleFormat = "%,.2f";
        rows = new ArrayList<Object[]>();
        Locale locale = new Locale("en");
        for (Map.Entry<TicketType, BigDecimal[]> entry: dbResult.entrySet()) {
            Object[] row = new Object[columnsCnt];
            BigDecimal[] vals = entry.getValue();

            row[0] = entry.getKey().getName();
            row[1] = String.format(locale, intFormat, vals[0].intValue());
            row[2] = String.format(locale, doubleFormat, vals[1]);
            row[3] = String.format(locale, doubleFormat, vals[2]);
            for (int i = 0; i < vals.length; i++) {
                footerVals[i] = footerVals[i].add(vals[i]);
            }
            rows.add(row);
        }
        footer = new Object[columnsCnt];
        footer[0] = "Итого";
        footer[1] = String.format(locale, intFormat, footerVals[0].intValue());
        footer[2] = String.format(locale, doubleFormat, footerVals[1]);
        footer[3] = String.format(locale, doubleFormat, footerVals[2]);
    }

    private void setEmpty() {
        // init zero values for table
        Map<TicketType, BigDecimal[]> map = new HashMap<TicketType, BigDecimal[]>();
        for (TicketType ticketType: TicketType.values()) {
            if (ticketType == TicketType.ALL) continue;
            BigDecimal[] d = new BigDecimal[3];
            for (int i = 0; i < d.length; i++) {
                d[i] = new BigDecimal(0);
            }
            map.put(ticketType, d);
        }
        setData(map);
    }
}
