package com.pb.dashboard.vitrina.core.config;

import com.pb.dashboard.core.db.DBManager;
import com.pb.dashboard.core.db.ResourceNames;
import com.pb.dashboard.vitrina.chart.data.ChartMData;
import com.pb.dashboard.vitrina.core.config.information.QueryInf;
import com.pb.dashboard.vitrina.payment.data.Metrics;
import com.pb.dashboard.vitrina.representation.payment.OldMetricsHolder;
import com.pb.dashboard.vitrina.statistics.byday.DayHourMetrics;
import com.pb.dashboard.vitrina.statistics.byday.DayMetrics;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

public class ASEDBManager extends DBManager {

    private static final Logger log = Logger.getLogger(ASEDBManager.class);
    private static final ResourceNames DATABASE = ResourceNames.VITRINA_METRICS;
    private static ASEDBManager instance;
    public List<Metrics> metrics;
    public List<Metrics> lastMetrics;
    public List<Metrics> sumMetrics;
    public List<DayHourMetrics> chartMDataAll;
    public List<Metrics> last2HourMetrics;
    public List<Metrics> last3HourMetrics;
    public List<Metrics> noneHourMetrics;
    public List<DayMetrics> sumMetricByPeriod;

    public List<Metrics> old1HourBackMetrics;
    public List<Metrics> old2HourBackMetrics;
    public List<Metrics> old3HourBackMetrics;
    private OldMetricsHolder oldMetricsHolder = new OldMetricsHolder(this);

    public List<ChartMData> chartMDataByPeriod;

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public ResourceNames getResource() {
        return DATABASE;
    }

    private ASEDBManager() {
        updateData();
    }

    public static ASEDBManager getInstance() {
        if (instance == null) {
            return instance = new ASEDBManager();
        }
        return instance;
    }

    public List<Metrics> getMetrics(String query) {
        List<Metrics> tempMetrics = new ArrayList<Metrics>();
        ResultSet rs = null;
        try {
            rs = getRSByStmt(query);
            while (rs.next()) {
                tempMetrics.add(new Metrics(rs.getInt("pkey"), rs.getString("name"), rs.getInt("value")));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return tempMetrics;
    }

    public List<ChartMData> getChartMData(String queryType, int twoMonthAgoDate, int curDate) {
        List<ChartMData> tempMetrics = new ArrayList<ChartMData>();
        ResultSet rs = null;
        try {
            rs = getRSByPrepStmt(queryType, twoMonthAgoDate, curDate);
            while (rs.next()) {
                tempMetrics.add(new ChartMData(rs.getInt("pkey"), rs.getInt("value"), rs.getString("day")));
            }
        } catch (Exception ex) {
            log.error(ex);
        }
        return tempMetrics;
    }

    public List<DayHourMetrics> getByDayMetrics(String queryType, int day) {
        List<DayHourMetrics> tempMetrics = new ArrayList<DayHourMetrics>();
        ResultSet rs = null;
        try {
            rs = getRSByPrepStmt(queryType, day);
            while (rs.next()) {
                tempMetrics.add(new DayHourMetrics(rs.getInt("pkey"), rs.getInt("hour"), rs.getInt("value")));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return tempMetrics;
    }

    public List<Metrics> getMetricsByPeriod(String query, int day) {
        List<Metrics> tempMetrics = new ArrayList<Metrics>();
        ResultSet rs = null;
        try {
            rs = getRSByPrepStmt(query, day);
            while (rs.next()) {
                tempMetrics.add(new Metrics(rs.getInt("pkey"), rs.getString("name"), rs.getInt("value")));
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
        return tempMetrics;
    }

    //very long time
    public List<DayMetrics> getSumMetricByPeriod(String query, int beginPeriod, int endPeriod) {
        List<DayMetrics> tempMetrics = new ArrayList<DayMetrics>();
        ResultSet rs = null;
        try {
            rs = getRSByPrepStmt(query, beginPeriod, endPeriod);
            while (rs.next()) {
                tempMetrics.add(new DayMetrics(rs.getInt("pkey"), "", rs.getInt("value"), rs.getInt("day")));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return tempMetrics;
    }

    public void updateData() {
        metrics = getMetrics(QueryInf.ALL_METRICS);
        sumMetrics = getMetrics(QueryInf.ALL_METRICS_SUMM);
        lastMetrics = getMetrics(QueryInf.ALL_METRICS_Pre);
        last2HourMetrics = getMetrics(QueryInf.ALL_METRICS_2Pre);
        last3HourMetrics = getMetrics(QueryInf.ALL_METRICS_3Pre);
        noneHourMetrics = getMetrics(QueryInf.ALL_NONE_HOUR_METRICS);
        Date today = new Date();
        chartMDataByPeriod = getChartMData(QueryInf.M_ALL, getTwoMonthAgoDate(), transformDate(today));
        chartMDataAll = getByDayMetrics(QueryInf.CURRENT_DAY_METRICS, transformDate(today));
        sumMetricByPeriod = getSumMetricByPeriod(QueryInf.METRIC_SUM_BY_PERIOD, 20140101, 20141231);

        // getOldMetrics
        int prevHour = getPrevHour();
        old1HourBackMetrics = oldMetricsHolder.getMetrics(getMonthBackDate(), prevHour - 1);
        old2HourBackMetrics = oldMetricsHolder.getMetrics(getMonthBackDate(), prevHour - 2);
        old3HourBackMetrics = oldMetricsHolder.getMetrics(getMonthBackDate(), prevHour - 3);

        log.info("Biplane Data Update");
    }

    private int getPrevHour() {
        SimpleDateFormat sdf = new SimpleDateFormat("k");
        Calendar cal = Calendar.getInstance();
        return Integer.parseInt(sdf.format(cal.getTime()));
    }

    private int getMonthBackDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, -4);
        return transformDate(cal.getTime());
    }

    private int transformDate(Date date) {
        Locale locale = new Locale("ru");
        DateFormat df = new SimpleDateFormat("yyyyMMdd", locale);
        return Integer.parseInt(df.format(date));
    }

    private int getTwoMonthAgoDate() {
        Calendar time = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyyMM");
        time.add(Calendar.MONTH, -2);
        return Integer.parseInt(df.format(time.getTime()) + "01");
    }

    public DecimalFormat setDecimalFormat() {
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' ');
        return new DecimalFormat("###,###,###.##", symbols);
    }
}