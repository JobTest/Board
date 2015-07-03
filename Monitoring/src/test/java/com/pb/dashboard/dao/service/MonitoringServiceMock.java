package com.pb.dashboard.dao.service;

import com.pb.dashboard.core.error.ErrorCode;
import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDescription;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDescriptionI;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDetail;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDetailI;
import com.pb.dashboard.dao.entity.biplanesupport.db.*;
import com.pb.dashboard.dao.entity.vitrinametrics.*;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.*;

/**
 * Created by vlad
 * Date: 06.04.15_15:25
 */
public class MonitoringServiceMock implements MonitoringServiceI {

    public static final String SESSION_ID = "session_id";
    public static final String SESSION_ID_EMPTY = "session_id_empty";
    public static final String SESSION_ID_EXC = "session_id_exc";

    @Override
    public Map<Integer, SlaInterfaceI> getSlaInterfaces(int interfacePkey, Parent parent) {
        return new LinkedHashMap<>();
    }

    @Override
    public Map<SlaInterfaceI, List<SlaTimingI>> getSlaTimingsByHp(int interfacePkey, LocalDate date, Parent parent) {
        return new LinkedHashMap<>();
    }

    @Override
    public Map<SlaInterfaceI, List<SlaTimingI>> getSlaTimingsByHour(int interfacePkey, LocalDate date, Parent parent) {
        return new LinkedHashMap<>();
    }

    @Override
    public Map<SlaInterfaceI, List<SlaTimingI>> getSlaTimingsByDays(int interfacePkey, LocalDate fromDate, LocalDate toDate, Parent parent) {
        return new LinkedHashMap<>();
    }

    @Override
    public Map<SlaInterfaceI, List<SlaCountI>> getSlaCountsByHp(int interfacePkey, LocalDate date, Parent parent) {
        return new LinkedHashMap<>();
    }

    @Override
    public Map<SlaInterfaceI, List<SlaCountI>> getSlaCountsByHour(int interfacePkey, LocalDate date, Parent parent) {
        return new LinkedHashMap<>();
    }

    @Override
    public Map<SlaInterfaceI, List<SlaCountI>> getSlaCountsByDays(int interfacePkey, LocalDate fromDate, LocalDate toDate, Parent parent) {
        return new LinkedHashMap<>();
    }

    @Override
    public Map<Integer, DInterfaceI> getInterfaces(int complex, int country) {
        Map<Integer, DInterfaceI> res = new LinkedHashMap<>();
        res.put(6, new DInterface(6, "create", "create description"));
        res.put(7, new DInterface(7, "delete", "delete description"));
        res.put(8, new DInterface(8, "searchPS", "searchPS description"));
        res.put(9, new DInterface(9, "loadSPR", "loadSPR description"));

        return res;

    }

    @Override
    public Map<Integer, InterfaceMetricI> getInterfaceMetrics(int interfacePkey) {
        if (interfacePkey == 5) {
            return new LinkedHashMap<>();
        }
        Map<Integer, InterfaceMetricI> res = new LinkedHashMap<>();
        res.put(1, new InterfaceMetric(1, "Общее время интерфейса", 1));
        res.put(2, new InterfaceMetric(2, "Обращение к БД", 0));
        res.put(3, new InterfaceMetric(3, "Работа Middleware", 0));
        return res;
    }

    @Override
    public InterfaceLimitI getInterfaceLimit(int interfacePkey) {
        return new InterfaceLimit(100, 150);
    }

    @Override
    public Map<SessionType, List<BpSession>> getSessionsWithoutDebt(String bpInterface, LocalDateTime dateFrom, LocalDateTime dateTo, int complexPKey) {
        return new LinkedHashMap<>();
    }

    @Override
    public Map<SessionType, List<BpSession>> getSessionsWithDebt(String bpInterface, LocalDateTime dateFrom, LocalDateTime dateTo, int complexPKey) {
        return new LinkedHashMap<>();
    }

    @Override
    public TableDataHolder getSessionDetails(String sessionId, int complexId) {
        return new TableDataHolder();
    }

    @Override
    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsBy10Min(DInterfaceI metric, LocalDate dateTime) {
        return null;
    }

    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsBy10Min(List<InterfaceMetricI> metrics, LocalDate dateTime) {
        return new LinkedHashMap<>();
    }

    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsByHour(List<InterfaceMetricI> metrics, LocalDate dateTime) {
        return new LinkedHashMap<>();
    }

    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsByDay(List<InterfaceMetricI> metrics, LocalDate from, LocalDate to) {
        return new LinkedHashMap<>();
    }

    public Map<String, Integer> getCompanyByErrTotal(ErrorsTypeI metric, int complexId, Integer interfaceId, Calendar date, int dateMonth, int count, String codeId, Integer consumerId) {
        return new LinkedHashMap<>();
    }

    public List<String> getSessionByCompanyErrTotal(ErrorsTypeI metric, int complexId, Integer interfaceId, Calendar date, int endDay, String errorCode, String companyId, Integer consumerId) {
        return new ArrayList<>();
    }

    public List<ErrorData> getErrTotal(int complexId, Integer interfaceId, Calendar date, int endDay, int count, ErrorsTypeI metric, Integer groupId, Integer responsibleId, Integer consumerId, Integer codeId) {
        return new ArrayList<>();
    }

    public <T> List<T> getSessionsByOuterSession(Complex complexFrom, Complex complex, String sessionId, Class<T> type) {
        if (complexFrom == Complex.BIPLANE_API2X && complex == Complex.DEBT && sessionId.equals(SESSION_ID) && type == SessionDebtData.class) {
            List data = new ArrayList<>();
            data.add(new SessionDebtData("12345"));
            data.add(new SessionDebtData("111"));
            data.add(new SessionDebtData("12345"));
            data.add(new SessionDebtData("12345"));
            data.add(new SessionDebtData("111"));
            data.add(new SessionDebtData("12345"));
            return data;
        }
        return new ArrayList<>();
    }

    public List<TimingData> getTimingsData(Complex complex, String sessionId) {
        return new ArrayList<>();
    }

    public List<SessionErrorsData> getSessionErrors(String sessionId) {
        if (SESSION_ID_EMPTY.equals(sessionId)) {
            return new ArrayList<>();
        }
        if (SESSION_ID_EXC.equals(sessionId)) {
            throw new RuntimeException(ErrorCode.SQL.ERROR_EXECUTE.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public ErrorDescriptionI getErrorDescription(String systemAndErrorCode) {
        return new ErrorDescription();
    }

    @Override
    public ErrorDetailI getErrorDetail(String systemAndErrorCode) {
        return new ErrorDetail();
    }
}
