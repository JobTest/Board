package com.pb.dashboard.server.dao.service.impl;

import com.pb.dashboard.server.dao.entity.iqlogrep.*;
import com.pb.dashboard.server.dao.service.IQDaoI;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad
 * Date: 05.03.15_10:43
 */
@Service
public class IQDao extends DaoAbs implements IQDaoI {

    public static final String ERROR_COUNT_DATE_PATTERN = "yyyy-MM-dd";
    private static final Logger log = Logger.getLogger(IQDao.class);

    @PersistenceUnit(unitName = "iqUnit")
    private EntityManagerFactory emf;

    @Override
    public EntityManagerFactory getEmf() {
        return emf;
    }

    @Override
    public List<RecipientEntity> getDebtSlaByCompany(String interfaceName, LocalDate date, int top) {
        return execNativeQueryList("call bpl.db_GetDebtSlaByCompany(?,?,?,?,?)",
                RecipientEntity.class, log,
                interfaceName, date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), top);
    }

    @Override
    public List<CountForCompany> getDebtErrByCompanyHour(String interfaceName, LocalDate date, int companyId) {
        return execNativeQueryList("call bpl.db_GetDebtErrByCompanyHour(?,?,?,?,?)",
                CountForCompany.class, CountForCompany.class, log,
                interfaceName, date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), companyId);
    }

    @Override
    public List<ErrorCountI> getErrorCountBy10Min(int complexPkey, BpInterface bpInterface) {
        String query = "call bpl.db_system_GetErrCnt2hr(?,?,?)";
        List<ErrorCount10Min> resultList = execNativeQueryList(query,
                ErrorCount10Min.class, log,
                complexPkey, bpInterface.getMain(), bpInterface.getPart());
        for (ErrorCount10Min count : resultList) {
            count.setBusiness(count.getBusiness() - count.get499Code());
        }
        return new ArrayList<ErrorCountI>(resultList);
    }

    @Override
    public List<ErrorCountI> getErrorCountByHour(int complexPkey, LocalDate date, BpInterface bpInterface) {
        String query = "call bpl.db_system_GetErrCntByHr(?,?,?,?)";
        List<ErrorCountHour> resultList = execNativeQueryList(query,
                ErrorCountHour.class, log,
                complexPkey, date.toString(ERROR_COUNT_DATE_PATTERN), bpInterface.getMain(), bpInterface.getPart());
        for (ErrorCountHour count : resultList) {
            count.setDate(date);
            count.setBusiness(count.getBusiness() - count.get499Code());
        }
        return new ArrayList<ErrorCountI>(resultList);
    }

    @Override
    public List<ErrorCountI> getErrorCountByDay(int complexPkey, LocalDate startDate, LocalDate endDate, BpInterface bpInterface) {
        String query = "call bpl.db_system_GetErrCntByDay(?,?,?,?,?)";
        List<ErrorCountDay> resultList = execNativeQueryList(query,
                ErrorCountDay.class, log,
                complexPkey,
                startDate.toString(ERROR_COUNT_DATE_PATTERN),
                endDate.toString(ERROR_COUNT_DATE_PATTERN),
                bpInterface.getMain(),
                bpInterface.getPart());
        for (ErrorCountDay count : resultList) {
            count.setDate(startDate);
            count.setBusiness(count.getBusiness() - count.get499Code());
        }
        return new ArrayList<ErrorCountI>(resultList);
    }

    @Override
    public List<ErrorCountI> getErrorCount6Month(int complexPkey, BpInterface bpInterface) {
        String query = "call bpl.db_system_GetErrCnt6Month(?,?,?)";
        List<ErrorCount6Month> resultList = execNativeQueryList(query,
                ErrorCount6Month.class, log,
                complexPkey,
                bpInterface.getMain(),
                bpInterface.getPart());
        for (ErrorCount6Month count : resultList) {
            count.setBusiness(count.getBusiness() - count.get499Code());
        }
        return new ArrayList<ErrorCountI>(resultList);
    }

    public List<InterfaceErrorCountI> getInterfaceErrorCounts(int complexPkey, int system, int year, int month, int day, Integer hour, Integer minute) {
        return execNativeQueryList("call bpl.db_system_GetQueryErrCnt(?,?,?,?,?,?,?)",
                InterfaceErrorCount.class, InterfaceErrorCountI.class, log,
                complexPkey, system, year, month, day, hour, minute);
    }
}
