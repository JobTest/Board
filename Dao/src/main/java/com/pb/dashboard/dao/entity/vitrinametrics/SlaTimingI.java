package com.pb.dashboard.dao.entity.vitrinametrics;

import org.joda.time.DateTime;

/**
 * Created by vlad
 * Date: 02.04.15_9:48
 */
public interface SlaTimingI {

    int getPkeySlaInterface();

    DateTime getDateTime();

    int getTimeTiming(Type type);

    enum Type {
        AVG,
        T90,
        T95,
        T99
    }
}