package com.pb.dashboard.external.monitor.view;

import com.pb.dashboard.external.monitor.entype.FilterRange;

import java.util.Date;

public interface Filterable {

    void filterUpdated(FilterRange range, Date[] date);
}
