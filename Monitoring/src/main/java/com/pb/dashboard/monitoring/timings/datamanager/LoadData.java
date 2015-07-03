package com.pb.dashboard.monitoring.timings.datamanager;

import com.pb.dashboard.core.util.IntegerUtil;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceLimitI;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import com.pb.dashboard.dao.service.ServiceFactory;
import org.joda.time.LocalDate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 17.12.14_15:01
 */
public class LoadData {

    public Map<Integer, InterfaceMetricI> getInterfaceMetrics(String pkeyInterface) {
        if (IntegerUtil.checkInt(pkeyInterface)) {
            int pkey = Integer.parseInt(pkeyInterface);
            return ServiceFactory.getMonitoring().getInterfaceMetrics(pkey);
        }
        return Collections.emptyMap();
    }

    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsBy10Min(List<InterfaceMetricI> metrics, LocalDate dateTime) {
        return ServiceFactory.getMonitoring().getTimingMetricsBy10Min(metrics, dateTime);
    }

    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsByHour(List<InterfaceMetricI> metrics, LocalDate dateTime) {
        return ServiceFactory.getMonitoring().getTimingMetricsByHour(metrics, dateTime);
    }

    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsByDay(List<InterfaceMetricI> metrics, LocalDate from, LocalDate to) {
        return ServiceFactory.getMonitoring().getTimingMetricsByDay(metrics, from, to);
    }

    public InterfaceLimitI getInterfaceLimit(Integer pkeyInterface) {
        return ServiceFactory.getMonitoring().getInterfaceLimit(pkeyInterface);
    }
}