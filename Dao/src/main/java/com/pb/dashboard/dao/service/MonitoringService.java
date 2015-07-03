package com.pb.dashboard.dao.service;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.util.StringUtil;
import com.pb.dashboard.dao.daomanager.DAOFactory;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDescription;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDescriptionI;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDetail;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDetailI;
import com.pb.dashboard.dao.entity.biplanesupport.db.*;
import com.pb.dashboard.dao.entity.vitrinametrics.*;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.*;

/**
 * Created by vlad
 * Date: 02.04.15_10:57
 */
public class MonitoringService implements MonitoringServiceI {

    private static final String SEPARATOR_CODE = "_";
    private static final int COUNT_CODE = 2;
    private static final int SYSTEM = 0;
    private static final int ERROR = 1;
    private DAOFactory dbManager;
    private static final Logger log = Logger.getLogger(MonitoringService.class);

    public MonitoringService(DAOFactory dbManager) {
        this.dbManager = dbManager;
    }

    public Map<Integer, SlaInterfaceI> getSlaInterfaces(int interfacePkey, Parent parent) {
        return dbManager.getVitrinaMetrics().getSlaInterfaces(interfacePkey, parent.getNumber());
    }

    public Map<SlaInterfaceI, List<SlaTimingI>> getSlaTimingsByHp(int interfacePkey, LocalDate date, Parent parent) {
        List<SlaTimingI> timings = dbManager.getVitrinaMetrics().getSlaTimingsHp(interfacePkey, date, parent.getNumber());
        return convertTimings(interfacePkey, parent, timings);
    }

    public Map<SlaInterfaceI, List<SlaTimingI>> getSlaTimingsByHour(int interfacePkey, LocalDate date, Parent parent) {
        List<SlaTimingI> timings = dbManager.getVitrinaMetrics().getSlaTimingsHour(interfacePkey, date, parent.getNumber());
        return convertTimings(interfacePkey, parent, timings);
    }

    public Map<SlaInterfaceI, List<SlaTimingI>> getSlaTimingsByDays(int interfacePkey, LocalDate fromDate, LocalDate toDate, Parent parent) {
        List<SlaTimingI> timings = dbManager.getVitrinaMetrics().getSlaTimingsDay(interfacePkey, fromDate, toDate, parent.getNumber());
        return convertTimings(interfacePkey, parent, timings);
    }

    public Map<SlaInterfaceI, List<SlaCountI>> getSlaCountsByHp(int interfacePkey, LocalDate date, Parent parent) {
        List<SlaCountI> counts = dbManager.getVitrinaMetrics().getSlaCountsHp(interfacePkey, date, parent.getNumber());
        return convertCounts(interfacePkey, parent, counts);
    }

    public Map<SlaInterfaceI, List<SlaCountI>> getSlaCountsByHour(int interfacePkey, LocalDate date, Parent parent) {
        List<SlaCountI> counts = dbManager.getVitrinaMetrics().getSlaCountsHour(interfacePkey, date, parent.getNumber());
        return convertCounts(interfacePkey, parent, counts);
    }

    public Map<SlaInterfaceI, List<SlaCountI>> getSlaCountsByDays(int interfacePkey, LocalDate fromDate, LocalDate toDate, Parent parent) {
        List<SlaCountI> counts = dbManager.getVitrinaMetrics().getSlaCountsDay(interfacePkey, fromDate, toDate, parent.getNumber());
        return convertCounts(interfacePkey, parent, counts);
    }

    @Override
    public Map<Integer, DInterfaceI> getInterfaces(int complex, int country) {
        return dbManager.getVitrinaMetrics().getInterfaces(complex, country);
    }

    @Override
    public Map<Integer, InterfaceMetricI> getInterfaceMetrics(int interfacePkey) {
        return dbManager.getVitrinaMetrics().getInterfaceMetrics(interfacePkey);
    }

    @Override
    public InterfaceLimitI getInterfaceLimit(int interfacePkey) {
        return dbManager.getVitrinaMetrics().getInterfaceLimit(interfacePkey);
    }

    @Override
    public Map<SessionType, List<BpSession>> getSessionsWithoutDebt(String bpInterface, LocalDateTime dateFrom, LocalDateTime dateTo, int complexPKey) {
        return dbManager.getIqdbManager().getSessionsWithoutDebt(bpInterface, dateFrom, dateTo, complexPKey);
    }

    @Override
    public Map<SessionType, List<BpSession>> getSessionsWithDebt(String bpInterface, LocalDateTime dateFrom, LocalDateTime dateTo, int complexPKey) {
        return dbManager.getIqdbManager().getSessionsWithDebt(bpInterface, dateFrom, dateTo, complexPKey);
    }

    @Override
    public TableDataHolder getSessionDetails(String sessionId, int complexId) {
        return dbManager.getIqdbManager().getSessionDetails(sessionId, complexId);
    }

    @Override
    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsBy10Min(DInterfaceI metric, LocalDate dateTime) {
        return dbManager.getAseDbManager().getTimingMetricsBy10Min(metric, dateTime);
    }

    @Override
    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsBy10Min(List<InterfaceMetricI> metrics, LocalDate dateTime) {
        return dbManager.getAseDbManager().getTimingMetricsBy10Min(metrics, dateTime);
    }

    @Override
    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsByHour(List<InterfaceMetricI> metrics, LocalDate dateTime) {
        return dbManager.getAseDbManager().getTimingMetricsByHour(metrics, dateTime);
    }

    @Override
    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsByDay(List<InterfaceMetricI> metrics, LocalDate from, LocalDate to) {
        return dbManager.getAseDbManager().getTimingMetricsByDay(metrics, from, to);
    }

    @Override
    public Map<String, Integer> getCompanyByErrTotal(ErrorsTypeI metric, int complexId, Integer interfaceId, Calendar date, int dateDay, int count, String codeId, Integer consumerId) {
        return dbManager.getIqdbManager().getCompanyByErrTotal(metric, complexId, interfaceId, date, dateDay, count, codeId, consumerId);
    }

    @Override
    public List<String> getSessionByCompanyErrTotal(ErrorsTypeI metric, int complexId, Integer interfaceId, Calendar date, int endDay, String errorCode, String companyId, Integer consumerId) {
        return dbManager.getIqdbManager().getSessionByCompanyErrTotal(metric, complexId, interfaceId, date, endDay, errorCode, companyId, consumerId);
    }

    @Override
    public List<ErrorData> getErrTotal(int complexId, Integer interfaceId, Calendar date, int endDay, int count, ErrorsTypeI metric, Integer groupId, Integer responsibleId, Integer consumerId, Integer codeId) {
        return dbManager.getIqdbManager().getErrTotal(complexId, interfaceId, date, endDay, count, metric, groupId, responsibleId, consumerId, codeId);
    }

    @Override
    public <T> List<T> getSessionsByOuterSession(Complex complexFrom, Complex complex, String sessionId, Class<T> type) {
        return dbManager.getIqdbManager().getSessionsByOuterSession(complexFrom, complex, sessionId, type);
    }

    @Override
    public List<TimingData> getTimingsData(Complex complex, String sessionId) {
        return dbManager.getIqdbManager().getTimingsData(complex, sessionId);
    }

    @Override
    public List<SessionErrorsData> getSessionErrors(String sessionId) {
        return dbManager.getIqdbManager().getSessionErrors(sessionId);
    }

    @Override
    public ErrorDescriptionI getErrorDescription(String systemAndErrorCode) {
        ErrorDescriptionI errorDescription;
        try {
            String[] codes = splitSystemAndErrorCode(systemAndErrorCode);
            errorDescription = dbManager.getBiplanePl2().getErrorDescription(codes[SYSTEM], codes[ERROR]);
        } catch (Exception e) {
            errorDescription = new ErrorDescription("");
            log.error(e.getMessage());
        }
        return errorDescription;
    }

    @Override
    public ErrorDetailI getErrorDetail(String systemAndErrorCode) {
        ErrorDetailI errorDetail;
        try {
            String[] codes = splitSystemAndErrorCode(systemAndErrorCode);
            errorDetail = dbManager.getBiplanePl2().getErrorDetail(codes[SYSTEM], codes[ERROR]);
        } catch (Exception e) {
            errorDetail = new ErrorDetail();
            log.error(e.getMessage());
        }
        return errorDetail;
    }

    private Map<SlaInterfaceI, List<SlaTimingI>> convertTimings(int interfacePkey, Parent parent, List<SlaTimingI> timings) {
        Map<SlaInterfaceI, List<SlaTimingI>> map = new LinkedHashMap<>();
        Map<Integer, SlaInterfaceI> slaInterfaces = getSlaInterfaces(interfacePkey, parent);
        for (SlaTimingI timing : timings) {

            SlaInterfaceI slaInterfaceI = slaInterfaces.get(timing.getPkeySlaInterface());
            List<SlaTimingI> slaTimings = map.get(slaInterfaceI);
            if (slaTimings == null) {
                slaTimings = new ArrayList<>();
                map.put(slaInterfaceI, slaTimings);
            }
            slaTimings.add(timing);
        }
        return map;
    }

    private Map<SlaInterfaceI, List<SlaCountI>> convertCounts(int interfacePkey, Parent parent, List<SlaCountI> counts) {
        Map<SlaInterfaceI, List<SlaCountI>> map = new LinkedHashMap<>();
        Map<Integer, SlaInterfaceI> slaInterfaces = getSlaInterfaces(interfacePkey, parent);
        for (SlaCountI count : counts) {
            SlaInterfaceI slaInterfaceI = slaInterfaces.get(count.getPkeySlaInterface());
            List<SlaCountI> slaTimingIs = map.get(slaInterfaceI);
            if (slaTimingIs == null) {
                slaTimingIs = new ArrayList<>();
                map.put(slaInterfaceI, slaTimingIs);
            }
            slaTimingIs.add(count);
        }
        return map;
    }

    /**
     * Разделение codes на system_code и code
     *
     * @param codes code error. example: KC_Err1210
     * @return two parts. example: "KC_" and "Err1210"
     */
    private static String[] splitSystemAndErrorCode(String codes) {
        if (StringUtil.isEmptyOrNull(codes)) {
            throw new IllegalArgumentException("Code is null or empty.");
        }
        String[] res = new String[COUNT_CODE];
        String[] split = codes.split(SEPARATOR_CODE, COUNT_CODE);
        res[SYSTEM] = split[SYSTEM] + SEPARATOR_CODE;
        res[ERROR] = split.length > 1 ? split[ERROR] : "";
        return res;
    }
}
