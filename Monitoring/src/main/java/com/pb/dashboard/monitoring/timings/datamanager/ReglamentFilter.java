package com.pb.dashboard.monitoring.timings.datamanager;

import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import org.joda.time.LocalDateTime;

import java.util.*;

/**
 * Created by vlad
 * Date: 23.12.14_17:01
 */
public class ReglamentFilter {

    public static final int REGLAMENT_HOUR = 3;

    public static Map<InterfaceMetricI, List<MetricItem>> filter(Map<InterfaceMetricI, List<MetricItem>> metricListMap,
                                                                FilterRange range, boolean reglament) {
        if (!valid(metricListMap, range, reglament)) {
            return metricListMap;
        }

        metricListMap = copy(metricListMap);
        for (List<MetricItem> items : metricListMap.values()) {
            Iterator<MetricItem> iterator = items.iterator();
            while (iterator.hasNext()) {
                MetricItem next = iterator.next();
                LocalDateTime dateTime = next.getDateTime();
                int hour = dateTime.getHourOfDay();
                if (hour < REGLAMENT_HOUR) {
                    iterator.remove();
                }
            }
        }
        return metricListMap;
    }

    private static boolean valid(Map<InterfaceMetricI, List<MetricItem>> metricListMap, FilterRange range, boolean reglament) {
        if (metricListMap == null) {
            return false;
        }
        if (range == null || range == FilterRange.DAY) {
            return false;
        }
        return !reglament;
    }

    private static Map<InterfaceMetricI, List<MetricItem>> copy(Map<InterfaceMetricI, List<MetricItem>> metricListMap) {
        Map<InterfaceMetricI, List<MetricItem>> res = new LinkedHashMap<>();
        for (Map.Entry<InterfaceMetricI, List<MetricItem>> entryMap : metricListMap.entrySet()) {
            List<MetricItem> items = new ArrayList<>(entryMap.getValue());
            res.put(entryMap.getKey(), items);
        }
        return res;
    }
}
