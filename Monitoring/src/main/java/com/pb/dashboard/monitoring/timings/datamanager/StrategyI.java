package com.pb.dashboard.monitoring.timings.datamanager;

import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 23.12.14_17:01
 */
public interface StrategyI {

    public int getReloadTimeMSec();

    public Map<InterfaceMetricI, List<MetricItem>> getTimingsMetrics(List<InterfaceMetricI> metrics, LocalDate date,
                                                                    LocalDate first, LocalDate second);
}
