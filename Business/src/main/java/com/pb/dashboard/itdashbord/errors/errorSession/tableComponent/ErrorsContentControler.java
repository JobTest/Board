package com.pb.dashboard.itdashbord.errors.errorSession.tableComponent;

import com.pb.dashboard.itdashbord.errors.db.ErrorsDBManager;
import com.pb.dashboard.itdashbord.errors.db.container.ErrByCompany;
import com.pb.dashboard.itdashbord.errors.errorSession.ErrorsSessionFiltersListener;

import java.util.ArrayList;
import java.util.List;

public class ErrorsContentControler {
    private TableView table ;
    private List<ErrByCompany> data;
    private List<ErrByCompany> newData = new ArrayList<ErrByCompany>();
    private String dateFrom;
    private String dateTo;
    private int companyId;
    private int number = 0;
    private ErrorsSessionFiltersListener filter;

    public List<ErrByCompany> getData() {
        return newData;
    }

    public int getNumber() {
        return number;
    }

    public TableView getTable() {
        return table;
    }

    public ErrorsContentControler(String dateFrom, String dateTo, int companyId, ErrorsSessionFiltersListener filter) {
        this.filter = filter;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.companyId = companyId;
        init();
    }

    private void init() {
        data = ErrorsDBManager.getInstance().getErrorsByCompany(dateFrom, dateTo, companyId);
        table = new TableView();
        for (ErrByCompany d : data){
            if (filterCheck(d)) {
                number += Integer.valueOf(d.getNumberOfErrors());
                newData.add(d);
            }
        }
        table.setData(newData);
    }


    private boolean filterCheck(ErrByCompany d) {
        if (filter.getType()==null||d.getErrorType().equals(filter.getType())||"NaN".equals(filter.getType())){
            if (filter.getSerchString()==null||"NaN".equals(filter.getSerchString())){return true;}
            else{
                String search = filter.getSerchString();
                if (search.length()>d.getErrorCode().length()){return false;}
                String dataSub = d.getErrorCode().substring(0, search.length());
                if (search.equals(dataSub)){
                    return true;
                }
            }
        }
        return false;
    }
}
