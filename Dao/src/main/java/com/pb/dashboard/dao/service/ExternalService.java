package com.pb.dashboard.dao.service;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.dao.daomanager.DAOFactory;
import com.pb.dashboard.dao.entity.biplanesupport.ErrorCountI;
import com.pb.dashboard.dao.entity.biplanesupport.db.BpSession;
import com.pb.dashboard.dao.entity.biplanesupport.db.InterfaceData;
import com.pb.dashboard.dao.entity.biplanesupport.db.TableDataHolder;
import com.pb.dashboard.dao.service.container.BpInterface;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 02.04.15_11:00
 */
public class ExternalService implements ExternalServiceI {

    public static final String BIPLANE = "biplane";
    public static final String EXTERNAL = "external";

    private DAOFactory dbManager;

    public ExternalService(DAOFactory dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public List<ErrorCountI> getErrorCountBy10Min(int complexPkey, String interfaceName) {
        return dbManager.getBiplaneSupport().getErrorCountBy10Min(complexPkey, splitByInterfaceName(interfaceName));
    }

    @Override
    public List<ErrorCountI> getErrorCountByHour(int complexPkey, LocalDate date, String interfaceName) {
        return dbManager.getBiplaneSupport().getErrorCountByHour(complexPkey, date, splitByInterfaceName(interfaceName));
    }

    @Override
    public List<ErrorCountI> getErrorCountByDay(int complexPkey, LocalDate startDate, LocalDate endDate, String interfaceName) {
        return dbManager.getBiplaneSupport().getErrorCountByDay(complexPkey, startDate, endDate, splitByInterfaceName(interfaceName));
    }

    @Override
    public List<ErrorCountI> getErrorCount6Month(int complexPkey, String interfaceName) {
        return dbManager.getBiplaneSupport().getErrorCount6Month(complexPkey, splitByInterfaceName(interfaceName));
    }

    @Override
    public TableDataHolder getSessionDetails(String sessionId, int complexId) {
        return dbManager.getIqdbManager().getSessionDetails(sessionId, complexId);
    }

    @Override
    public Map<String, List<InterfaceData>> getRecStatsByHour(String[] nameInterfaces) {
        return dbManager.getIqdbManager().getRecStatsByHour(nameInterfaces);
    }

    @Override
    public Map<String, List<InterfaceData>> getTempStatsByHour(String[] nameInterfaces) {
        return dbManager.getIqdbManager().getTempStatsByHour(nameInterfaces);
    }

    @Override
    public Map<String, Integer> getQueryErrCnt(Complex complex, boolean isSystemErrorsChart, int year, int month, Integer day, Integer hour, Integer minute) {
        return dbManager.getIqdbManager().getQueryErrCnt(complex, isSystemErrorsChart, year, month, day, hour, minute);
    }

    @Override
    public Map<Integer, Integer> getErrorCntByStatus(Complex complex, boolean system, int year, int month, Integer day, Integer hour, Integer minute, String interfaceName) {
        return dbManager.getIqdbManager().getErrorCntByStatus(complex, system, year, month, day, hour, minute, splitByInterfaceName(interfaceName));
    }

    @Override
    public List<BpSession> getSessionsByErrStatus(Complex complex, int year, int month, Integer day, Integer hour, Integer minute, String interfaceName, String sessionStatus) {
        return dbManager.getIqdbManager().getSessionsByErrStatus(complex, year, month, day, hour, minute, splitByInterfaceName(interfaceName), sessionStatus);
    }

    //must have 2 params
    private BpInterface splitByInterfaceName(String name) {
        if (name == null) {
            return new BpInterface();
        } else if (name.startsWith(BIPLANE)) {
            return complex(BIPLANE, name);
        } else if (name.startsWith(EXTERNAL)) {
            return complex(EXTERNAL, name);
        }
        return new BpInterface(name, null);
    }

    private BpInterface complex(String complex, String interfaceName) {
        String secondPart = interfaceName.substring(complex.length());
        secondPart = secondPart.replaceFirst("^_", "");
        return new BpInterface(complex, secondPart);
    }
}
