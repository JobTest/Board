package com.pb.dashboard.dao.daomanager;

import com.pb.dashboard.dao.entity.vitrinametrics.*;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 01.04.15_12:05
 */
public interface VitrinaMetricsDAOI {

    Map<Integer, SlaInterfaceI> getSlaInterfaces(int interfacePkey, int parent);

    List<SlaTimingI> getSlaTimingsHp(int interfacePkey, LocalDate date, int parent);

    List<SlaTimingI> getSlaTimingsHour(int interfacePkey, LocalDate date, int parent);

    List<SlaTimingI> getSlaTimingsDay(int interfacePkey, LocalDate fromDate, LocalDate toDate, int parent);

    List<SlaCountI> getSlaCountsHp(int interfacePkey, LocalDate date, int parent);

    List<SlaCountI> getSlaCountsHour(int interfacePkey, LocalDate date, int parent);

    List<SlaCountI> getSlaCountsDay(int interfacePkey, LocalDate fromDate, LocalDate toDate, int parent);

    Map<Integer, DInterfaceI> getInterfaces(int complex, int country);

    Map<Integer, InterfaceMetricI> getInterfaceMetrics(int interfacePkey);

    InterfaceLimitI getInterfaceLimit(int interfacePkey);
}
