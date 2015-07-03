package com.pb.dashboard.dao.dbmanager;

import com.pb.dashboard.core.db.DBManager;
import com.pb.dashboard.core.db.ResourceNames;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import com.pb.dashboard.dao.service.ServiceFactory;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.sql.ResultSet;
import java.util.*;

public final class AseDbManager extends DBManager {

    private static final Logger log = Logger.getLogger(AseDbManager.class);
    private static final ResourceNames DATABASE = ResourceNames.VITRINA_METRICS;

    private static AseDbManager instance;

    @Override

    public Logger getLog() {
        return log;
    }

    @Override
    public ResourceNames getResource() {
        return DATABASE;
    }

    public static synchronized AseDbManager getInstance() {
        if (instance == null) {
            instance = new AseDbManager();
        }
        return instance;
    }

    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsBy10Min(DInterfaceI bpInterface, LocalDate date) {
        Collection<InterfaceMetricI> values = ServiceFactory.getMonitoring().getInterfaceMetrics(bpInterface.getPkey()).values();
        return getTimingMetricsBy10Min(values, date);
    }

    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsBy10Min(Collection<InterfaceMetricI> interfaceMetrics, LocalDate date) {
        Map<InterfaceMetricI, List<MetricItem>> result = new HashMap<>();
        String query = AseQueryBuilder.getTimingMetricsByIntMetricQuery();
        ResultSet rs;
        for (InterfaceMetricI metric : interfaceMetrics) {
            try {
                rs = getRSByCallStmt(query, metric.getPkey(), convertDayToInt(date));
                List<MetricItem> data = new ArrayList<MetricItem>();
                while (rs.next()) {
                    MetricItem metricItem = new MetricItem();
                    int dateInt = rs.getInt("date");
                    int hour = rs.getInt("hour");
                    int minute = rs.getInt("hour_part");
                    LocalDateTime dateTime = convertIntToDate(dateInt, hour, minute);
                    metricItem.setDateTime(dateTime);
                    metricItem.setMin(rs.getInt("timing_min"));
                    metricItem.setMax(rs.getInt("timing_max"));
                    metricItem.setAvg(rs.getInt("timing_avg"));
                    metricItem.setT90(rs.getInt("timing_90"));
                    metricItem.setT95(rs.getInt("timing_95"));
                    metricItem.setT99(rs.getInt("timing_99"));
                    metricItem.setCount(rs.getInt("cnt"));
                    metricItem.setErrorCount(rs.getInt("error_count"));
                    data.add(metricItem);
                }
                result.put(metric, data);
            } catch (Exception e) {
                log.error("Not executed getTimingMetric.", e);
            }
        }
        return result;
    }

    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsByHour(Collection<InterfaceMetricI> metrics, LocalDate date) {
        Map<InterfaceMetricI, List<MetricItem>> res = new LinkedHashMap<>();
        String query = AseQueryBuilder.getTimingMetricsForDayQuery();
        for (InterfaceMetricI metric : metrics) {
            try {
                int dateInt = convertDayToInt(date);
                ResultSet rs = getRSByCallStmt(query, metric.getPkey(), dateInt);
                List<MetricItem> metricList = new ArrayList<MetricItem>();
                while (rs.next()) {
                    MetricItem metricItem = new MetricItem();
                    int day = rs.getInt(1);
                    int hour = rs.getInt("hour");
                    LocalDateTime resCal = convertIntToDate(day, hour, 0);
                    metricItem.setDateTime(resCal);
                    metricItem.setMin(rs.getInt("timing_min"));
                    metricItem.setMax(rs.getInt("timing_max"));
                    metricItem.setAvg(rs.getInt("timing_avg"));
                    metricItem.setT90(rs.getInt("timing_90"));
                    metricItem.setT95(rs.getInt("timing_95"));
                    metricItem.setT99(rs.getInt("timing_99"));
                    metricItem.setCount(rs.getInt("cnt"));
                    metricItem.setErrorCount(rs.getInt("error_count"));
                    metricList.add(metricItem);
                }
                res.put(metric, metricList);
            } catch (Exception e) {
                log.error("Not executed getTimingMetricsDataByHour", e);
            }
        }
        return res;
    }

    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsByDay(Collection<InterfaceMetricI> metrics, LocalDate from, LocalDate to) {
        Map<InterfaceMetricI, List<MetricItem>> res = new LinkedHashMap<>();
        String query = AseQueryBuilder.getTimingMetricsByDaysQuery();
        for (InterfaceMetricI metric : metrics) {
            try {
                int fromInt = convertDayToInt(from);
                int toInt = convertDayToInt(to);
                ResultSet rs = getRSByCallStmt(query, metric.getPkey(), fromInt, toInt);
                List<MetricItem> metricList = new ArrayList<MetricItem>();
                while (rs.next()) {
                    MetricItem metricItem = new MetricItem();
                    int date = rs.getInt("date");
                    LocalDateTime dateTime = convertIntToDate(date, 0, 0);
                    metricItem.setDateTime(dateTime);
                    metricItem.setMin(rs.getInt("timing_min"));
                    metricItem.setMax(rs.getInt("timing_max"));
                    metricItem.setAvg(rs.getInt("timing_avg"));
                    metricItem.setT90(rs.getInt("timing_90"));
                    metricItem.setT95(rs.getInt("timing_95"));
                    metricItem.setT99(rs.getInt("timing_99"));
                    metricItem.setCount(rs.getInt("cnt"));
                    metricItem.setErrorCount(rs.getInt("error_count"));
                    metricList.add(metricItem);
                }
                res.put(metric, metricList);
            } catch (Exception e) {
                log.error("Not executed getTimingMetricDataByDay", e);
            }
        }
        return res;
    }

    private LocalDateTime convertIntToDate(int date, int hour, int minute) {
        int year = date / 10000;
        int month = date / 100 % 100;
        int day = date % 100;
        return new LocalDateTime(year, month, day, hour, minute);
    }

    private int convertDayToInt(LocalDate dateTime) {
        String date = dateTime.toString("yyyyMMdd");
        return Integer.parseInt(date);
    }
}