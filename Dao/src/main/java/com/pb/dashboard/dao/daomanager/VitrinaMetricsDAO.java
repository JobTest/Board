package com.pb.dashboard.dao.daomanager;

import com.pb.dashboard.dao.entity.vitrinametrics.*;
import org.joda.time.LocalDate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 01.04.15_12:06
 */
public class VitrinaMetricsDAO extends DashEntityManager implements VitrinaMetricsDAOI {

    public static final String UNIT_NAME = "vitrinaMetrics";

    public VitrinaMetricsDAO() {
        super(UNIT_NAME);
    }

    @Override
    public Map<Integer, SlaInterfaceI> getSlaInterfaces(int interfacePkey, int parent) {
        List<SlaInterface> resultList = execNativeQueryList("exec dbo.GetSlaMetricsByParent ?,?",
                SlaInterface.class,
                interfacePkey,
                parent);
        Map<Integer, SlaInterfaceI> res = new LinkedHashMap<>();
        for (SlaInterface slaInterface : resultList) {
            res.put(slaInterface.getPkey(), slaInterface);
        }
        return res;
    }

    public List<SlaTimingI> getSlaTimingsHp(int interfacePkey, LocalDate date, int parent) {
        return execNativeQueryList("exec dbo.GetSlaMetricsByHp ?,?,?,'time'",
                SlaTimingHp.class, SlaTimingI.class,
                interfacePkey,
                convertDate(date),
                parent);
    }

    public List<SlaTimingI> getSlaTimingsHour(int interfacePkey, LocalDate date, int parent) {
        return execNativeQueryList("exec dbo.GetSlaMetricsByHour ?,?,?,'time'",
                SlaTimingHour.class, SlaTimingI.class,
                interfacePkey,
                convertDate(date),
                parent);
    }

    public List<SlaTimingI> getSlaTimingsDay(int interfacePkey, LocalDate fromDate, LocalDate toDate, int parent) {
        return execNativeQueryList("exec dbo.GetSlaMetricsByDays ?,?,?,?,'time'",
                SlaTimingDay.class, SlaTimingI.class,
                interfacePkey,
                convertDate(fromDate),
                convertDate(toDate),
                parent);
    }

    public List<SlaCountI> getSlaCountsHp(int interfacePkey, LocalDate date, int parent) {
        return execNativeQueryList("exec dbo.GetSlaMetricsByHp ?,?,?,'sla'",
                SlaCountHp.class, SlaCountI.class,
                interfacePkey,
                convertDate(date),
                parent);
    }

    public List<SlaCountI> getSlaCountsHour(int interfacePkey, LocalDate date, int parent) {
        return execNativeQueryList("exec dbo.GetSlaMetricsByHour ?,?,?,'sla'",
                SlaCountHour.class, SlaCountI.class,
                interfacePkey,
                convertDate(date),
                parent);
    }

    public List<SlaCountI> getSlaCountsDay(int interfacePkey, LocalDate fromDate, LocalDate toDate, int parent) {
        return execNativeQueryList("exec dbo.GetSlaMetricsByDays ?,?,?,?,'sla'",
                SlaCountDay.class, SlaCountI.class,
                interfacePkey,
                convertDate(fromDate),
                convertDate(toDate),
                parent);
    }

    public Map<Integer, DInterfaceI> getInterfaces(int complex, int country) {
        List<DInterfaceI> resultList = execNativeQueryList("exec dbo.GetInterfacesForComplex ?,?",
                DInterface.class, DInterfaceI.class,
                complex,
                country);
        Map<Integer, DInterfaceI> res = new LinkedHashMap<>();
        for (DInterfaceI dInterfaceI : resultList) {
            res.put(dInterfaceI.getPkey(), dInterfaceI);
        }
        return res;
    }

    public Map<Integer, InterfaceMetricI> getInterfaceMetrics(int interfacePkey) {
        List<InterfaceMetricI> resultList = execNativeQueryList("exec dbo.GetInterMetricsForInterface ?",
                InterfaceMetric.class, InterfaceMetricI.class,
                interfacePkey);
        Map<Integer, InterfaceMetricI> res = new LinkedHashMap<>();
        for (InterfaceMetricI interfaceMetricI : resultList) {
            res.put(interfaceMetricI.getPkey(), interfaceMetricI);
        }
        return res;
    }

    public InterfaceLimitI getInterfaceLimit(int interfacePkey) {
        return execNativeQueryOne("select warning_limit, critical_limit FROM dbo.interfaces WHERE pkey = ?",
                InterfaceLimit.class,
                interfacePkey);
    }

    private int convertDate(LocalDate date) {
        String yyyyMMdd = date.toString("yyyyMMdd");
        return Integer.valueOf(yyyyMMdd);
    }
}