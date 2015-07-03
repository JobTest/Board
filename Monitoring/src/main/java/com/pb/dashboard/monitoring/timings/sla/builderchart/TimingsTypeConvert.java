package com.pb.dashboard.monitoring.timings.sla.builderchart;

import com.pb.dashboard.dao.entity.vitrinametrics.SlaTimingI;

/**
 * Created by vlad
 * Date: 03.04.15_12:30
 */
public class TimingsTypeConvert {

    public static SlaTimingI.Type toSlaTimings(TimingsType type) {
        switch (type) {
            case AVG:
                return SlaTimingI.Type.AVG;
            case PERCENT_90:
                return SlaTimingI.Type.T90;
            case PERCENT_95:
                return SlaTimingI.Type.T95;
            case PERCENT_99:
                return SlaTimingI.Type.T99;
        }
        throw new IllegalArgumentException("TimingsType[" + type + "] is not exists.");
    }
}
