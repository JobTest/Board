package com.pb.dashboard.external.charts;

import com.pb.dashboard.external.charts.view.PageView;
import com.vaadin.server.Page;
import com.vaadin.ui.VerticalLayout;

public class ChartsView extends VerticalLayout{

    private static final long serialVersionUID = -3857855230770858343L;
    private String dateFrom;
    private String dateTo;
    private String filterValue;
    private int bank;

    public ChartsView() {
        Page.getCurrent().setTitle("Charts");
        setSizeFull();
        splitUrl();
        addComponent(new PageView(bank, dateFrom, dateTo, filterValue));
    }

    private void splitUrl() {
        String[] s = Page.getCurrent().getUriFragment().split("/");
        filterValue = s [s.length - 4];
        if (filterValue.equals("hourly")) {
            dateFrom = "";
            for (int i = s.length - 1; i > s.length - 4; i--) {
                if (i != s.length - 1) {
                    dateFrom += "-" + s[i];
                } else {
                    dateFrom += s[i];
                }
            }
            bank = Integer.valueOf(s[s.length - 5]);
        }
        else{
            filterValue = s [s.length - 7];
            dateFrom = "";
            dateTo = "";
            for (int i = s.length - 4; i > s.length - 7; i--) {
                if (i != s.length - 4) {
                    dateFrom += "-" + s[i];
                    dateTo +="-"+s[i+3];
                } else {
                    dateFrom += s[i];
                    dateTo += s[i+3];
                }
            }
            bank = Integer.valueOf(s[s.length - 8]);
        }
    }
}