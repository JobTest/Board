package com.pb.dashboard.monitoring.errors.outer;

import com.pb.dashboard.monitoring.errors.outer.table.OuterSessionData;

import java.util.List;

/**
 * Created by evasive on 03.11.14.
 */
public class OuterModel {

    private String outerSessionId;

    private List<OuterSessionData> outerSessionList;

    public String getOuterSessionId() {
        return outerSessionId;
    }

    public void setOuterSessionId(String outerSessionId) {
        this.outerSessionId = outerSessionId;
    }

    public List<OuterSessionData> getOuterSessionList() {
        return outerSessionList;
    }

    public void setOuterSessionList(List<OuterSessionData> outerSessionList) {
        this.outerSessionList = outerSessionList;
    }
}

