package com.pb.dashboard.dao.entity.vitrinametrics;

import org.joda.time.DateTime;

/**
 * Created by vlad
 * Date: 02.04.15_12:30
 */
public interface SlaCountI {

    int getPkeySlaInterface();

    DateTime getDateTime();

    int getCount();

    int getErrorCount();

    double getPercent();

}
