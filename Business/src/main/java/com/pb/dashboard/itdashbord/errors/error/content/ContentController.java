package com.pb.dashboard.itdashbord.errors.error.content;

import com.pb.dashboard.itdashbord.errors.ErrorsFiltersListener;
import com.pb.dashboard.itdashbord.errors.db.ErrorsDBManager;
import com.pb.dashboard.itdashbord.errors.db.container.ErrorsData;
import com.pb.dashboard.itdashbord.errors.error.content.table.ErrorTable;
import com.vaadin.event.ItemClickEvent;

import java.util.ArrayList;
import java.util.List;

public class ContentController {

    private ContentView view;
    private List<ErrorsData> data;
    private List<ErrorsData> settedData = new ArrayList<ErrorsData>();
    private ErrorsFiltersListener filter;

    public List<ErrorsData> getSettedData() {
        return settedData;
    }

    public ContentController(ErrorsFiltersListener filter) {
        this.filter =filter;
        data = ErrorsDBManager.getInstance().getErrorsDataList();
        init();
    }

    private void init() {
        data = ErrorsDBManager.getInstance().getErrorsDataList();
        view = new ContentView();
        setErrorsData();
        view.getTable().setData(settedData, filter);
        addLinks();
    }

    public void tableRepaint(){
        settedData.clear();
        view = new ContentView();
        setErrorsData();
        view.getTable().setData(settedData, filter);
        addLinks();
    }

    private void addLinks() {
        view.getTable().getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                ArrayList<ErrorsData> branchData = setBranchData(itemClickEvent.getItem().getItemProperty(ErrorTable.BRANCH).toString());
            }
        });
    }

    private ArrayList<ErrorsData> setBranchData(String branch){
        ArrayList<ErrorsData> arr = new ArrayList<ErrorsData>();
        for (ErrorsData data : settedData){
            if (branch.equals(data.getBranch())){
                arr.add(data);
            }
        }
        return arr;
    }

    private void setErrorsData() {
        for (ErrorsData d : data){
            if (isAbleToAdd(d.getBranch(),d.getGroupType(),d.getType(), filter)){
                if (filter.getSearchString()==null||"NaN".equals(filter.getSearchString())){
                    settedData.add(d);
                }
                else {
                    String search = filter.getSearchString();
                    if (search.length()<=d.getId().length()){
                        String requstedVal = d.getId().substring(0,search.length());
                        if (requstedVal.equals(search)){
                            settedData.add(d);
                        }
                    }
                }
            }
        }
    }

    private boolean isAbleToAdd(String branch, String groupType, String type, ErrorsFiltersListener filter) {
        if (!(filter.getBaranch()==null||"NaN".equals(filter.getBaranch())||filter.getBaranch().equals(branch))) return false;
        if (!(filter.getGroup()==null||"NaN".equals(filter.getGroup())||filter.getGroup().equals(groupType))) return false;
        return filter.getStypeDebt() == null || "NaN".equals(filter.getStypeDebt()) || filter.getStypeDebt().equals(type);
    }

    public ContentView getView() {
        return view;
    }
}
