package com.pb.dashboard.dao.service;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDescriptionI;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDetailI;
import com.pb.dashboard.dao.entity.biplanesupport.db.*;
import com.pb.dashboard.dao.entity.vitrinametrics.*;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import org.boon.Str;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 02.04.15_10:57
 */
public interface MonitoringServiceI {

    Map<Integer, SlaInterfaceI> getSlaInterfaces(int interfacePkey, Parent parent);

    Map<SlaInterfaceI, List<SlaTimingI>> getSlaTimingsByHp(int interfacePkey, LocalDate date, Parent parent);

    Map<SlaInterfaceI, List<SlaTimingI>> getSlaTimingsByHour(int interfacePkey, LocalDate date, Parent parent);

    Map<SlaInterfaceI, List<SlaTimingI>> getSlaTimingsByDays(int interfacePkey, LocalDate fromDate, LocalDate toDate, Parent parent);

    Map<SlaInterfaceI, List<SlaCountI>> getSlaCountsByHp(int interfacePkey, LocalDate date, Parent parent);

    Map<SlaInterfaceI, List<SlaCountI>> getSlaCountsByHour(int interfacePkey, LocalDate date, Parent parent);

    Map<SlaInterfaceI, List<SlaCountI>> getSlaCountsByDays(int interfacePkey, LocalDate fromDate, LocalDate toDate, Parent parent);

    Map<Integer, DInterfaceI> getInterfaces(int complex, int country);

    Map<Integer, InterfaceMetricI> getInterfaceMetrics(int interfacePkey);

    InterfaceLimitI getInterfaceLimit(int interfacePkey);

    Map<SessionType, List<BpSession>> getSessionsWithoutDebt(String bpInterface, LocalDateTime dateFrom, LocalDateTime dateTo, int complexPKey);

    Map<SessionType, List<BpSession>> getSessionsWithDebt(String bpInterface, LocalDateTime dateFrom, LocalDateTime dateTo, int complexPKey);

    TableDataHolder getSessionDetails(String sessionId, int complexId);

    Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsBy10Min(DInterfaceI metric, LocalDate dateTime);

    Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsBy10Min(List<InterfaceMetricI> metrics, LocalDate dateTime);

    Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsByHour(List<InterfaceMetricI> metrics, LocalDate dateTime);

    Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsByDay(List<InterfaceMetricI> metrics, LocalDate from, LocalDate to);

    Map<String, Integer> getCompanyByErrTotal(ErrorsTypeI metric, int complexId, Integer interfaceId, Calendar date, int dateMonth, int count, String codeId, Integer consumerId);

    List<String> getSessionByCompanyErrTotal(ErrorsTypeI metric, int complexId, Integer interfaceId, Calendar date, int endDay, String errorCode, String companyId, Integer consumerId);

    List<ErrorData> getErrTotal(int complexId, Integer interfaceId, Calendar date, int endDay, int count, ErrorsTypeI metric, Integer groupId, Integer responsibleId, Integer consumerId, Integer codeId);

    <T> List<T> getSessionsByOuterSession(Complex complexFrom, Complex complex, String sessionId, Class<T> type);

    List<TimingData> getTimingsData(Complex complex, String sessionId);

    List<SessionErrorsData> getSessionErrors(String sessionId);

    ErrorDescriptionI getErrorDescription(String systemAndErrorCode);

    ErrorDetailI getErrorDetail(String systemAndErrorCode);


    enum Parent {
        ALL(-1),
        CHILD(0),
        PARENT(1);

        private final int number;

        Parent(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }

}
