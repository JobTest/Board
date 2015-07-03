package com.pb.dashboard.quality.view;

import com.pb.dashboard.quality.types.Channel;
import com.pb.dashboard.quality.types.Range;

import java.util.Date;

public interface Filterable {

    public void filterDataUpdated(Channel channel, Range range, Date[] dates);

}
