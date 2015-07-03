package com.pb.dashboard.dao.service;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.dao.entity.biplanesupport.ErrorCountI;
import com.pb.dashboard.dao.entity.biplanesupport.db.BpSession;
import com.pb.dashboard.dao.entity.biplanesupport.db.InterfaceData;
import com.pb.dashboard.dao.entity.biplanesupport.db.TableDataHolder;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 02.04.15_11:00
 */
public interface ExternalServiceI {

    List<ErrorCountI> getErrorCountBy10Min(int complexPkey, String interfaceName);

    List<ErrorCountI> getErrorCountByHour(int complexPkey, LocalDate date, String interfaceName);

    List<ErrorCountI> getErrorCountByDay(int complexPkey, LocalDate startDate, LocalDate endDate, String interfaceName);

    List<ErrorCountI> getErrorCount6Month(int complexPkey, String interfaceName);

    TableDataHolder getSessionDetails(String sessionId, int complexId);

    Map<String, List<InterfaceData>> getRecStatsByHour(String[] nameInterfaces);

    Map<String, List<InterfaceData>> getTempStatsByHour(String[] nameInterfaces);

    Map<String, Integer> getQueryErrCnt(Complex complex, boolean isSystemErrorsChart, int year, int month, Integer day, Integer hour, Integer minute);

    Map<Integer, Integer> getErrorCntByStatus(Complex complex, boolean system, int year, int month, Integer day, Integer hour, Integer minute, String interfaceName);

    List<BpSession> getSessionsByErrStatus(Complex complex, int year, int month, Integer day, Integer hour, Integer minute, String interfaceName, String sessionStatus);

}
