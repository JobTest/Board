package com.pb.dashboard.dao.dbmanager;

public class AseQueryBuilder {

    public static final String PKEY_INT = "pkey_int";
    public static final String PKEY = "pkey";
    public static final String NAME = "int_name";
    public static final String DESCRIPTION = "description";
    public static final String RESULT_SET_FIELDS = "result_set_fields";

    private AseQueryBuilder() {
    }

    /**
     * Get query list interfaces for Country and Complex
     * countryPkey
     * complexPkey
     *
     * @return list interfaces
     * @see com.pb.dashboard.core.model.Country
     * @see com.pb.dashboard.core.model.Complex
     */
    public static String getInterfacesForComplexQuery() {
        return "dbo.GetInterfacesForComplex ?, ?";
    }


    /**
     * Get query list metrics for Interface
     * interfacePkey
     *
     * @return list metrics
     * @see com.pb.dashboard.core.model.BpInterface
     * @see com.pb.dashboard.monitoring.components.model.InterfaceMetric
     */
    public static String getInterMetricsForInterfaceQuery() {
        return "dbo.GetInterMetricsForInterface ?";
    }

    /**
     * Get query timingMetrics by every 10 minute for Interface
     * pkeyMetric
     * date, int in format yyyyMMdd. Example: 20141210.
     *
     * @return list TimingMetrics
     * @see com.pb.dashboard.core.model.BpInterface
     * @see com.pb.dashboard.monitoring.db.ase.container.MetricItem
     */
    public static String getTimingMetricsByIntMetricQuery() {
        return "dbo.GetTimingMetricsByIntMetric ?, ?";
    }

    /**
     * Get query timingMetrics by every hour for Interface
     * pkeyInterface
     * date, int in format yyyyMMdd. Example: 20141210.
     *
     * @return list TimingMetrics
     * @see com.pb.dashboard.core.model.BpInterface
     * @see com.pb.dashboard.monitoring.db.ase.container.MetricItem
     */
    public static String getTimingMetricsForDayQuery() {
        return "dbo.GetTimingMetricsForDay ?, ?";
    }

    /**
     * Get query timingMetrics by every day for Interface
     * pkeyInterface
     * date from, int in format yyyyMMdd. Example: 20141210.
     * date to, int in format yyyyMMdd. Example: 20141210.
     *
     * @return list TimingMetric by 1 day
     * @see com.pb.dashboard.core.model.BpInterface
     * @see com.pb.dashboard.monitoring.db.ase.container.MetricItem
     */
    public static String getTimingMetricsByDaysQuery() {
        return "dbo.GetTimingMetricsByDays ?, ?, ?";
    }

    /**
     * Get query limits for Interface
     * pkeyInterface
     *
     * @return warning and critical limits
     * @see com.pb.dashboard.monitoring.db.ase.container.Limit
     */
    public static String selectLimits() {
        return "SELECT warning_limit, critical_limit FROM dbo.interfaces WHERE pkey = ?";
    }
}