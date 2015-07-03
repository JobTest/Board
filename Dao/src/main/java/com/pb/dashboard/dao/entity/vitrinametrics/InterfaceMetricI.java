package com.pb.dashboard.dao.entity.vitrinametrics;

/**
 * Created by vlad
 * Date: 06.04.15_14:21
 */
public interface InterfaceMetricI {

    int MAIN_METRICS = 1;

    int getPkey();

    String getDescription();

    boolean isMain();

}
