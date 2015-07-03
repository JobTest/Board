package com.pb.dashboard.tickets.view.chart;

import com.pb.dashboard.tickets.entype.Branch;
import com.vaadin.addon.charts.model.ListSeries;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BranchChartDataHolder {

    private String[] categories;
    private ListSeries series;

    public BranchChartDataHolder() {
        setEmpty();
    }

    public BranchChartDataHolder(Map<Branch, Integer> dbResult) {
        setData(dbResult);
    }

    public String[] getCategories() {
        return categories;
    }

    public ListSeries getSeries() {
        return series;
    }

    private void setData(Map<Branch, Integer> dbResult) {
        setCategories(dbResult.keySet());
        series = new ListSeries();
        for (Integer value : dbResult.values()) {
            series.addData(value);
        }
    }

    private void setCategories(Set<Branch> branches) {
        categories = new String[branches.size()];
        int i = 0;
        for (Branch branch: branches) {
            categories[i++] = branch.getName();
        }
    }

    private void setEmpty() {
        Map<Branch, Integer> map = new HashMap<Branch, Integer>();
        for (Branch branch: Branch.values()) {
            if (branch == Branch.UNDEF) continue;
            map.put(branch, 0);
        }
        setData(map);
    }

}
