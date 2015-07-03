package com.pb.dashboard.dao.daomanager;

import com.pb.dashboard.dao.entity.biplanesupport.*;
import com.pb.dashboard.dao.service.container.BpInterface;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad
 * Date: 01.04.15_12:38
 */
public class BiplaneSupportDAO extends DashEntityManager implements BiplaneSupportDAOI {

    public static final String ERROR_COUNT_DATE_PATTERN = "yyyy-MM-dd";

    public static final String UNIT_NAME = "supportUnit";

    public BiplaneSupportDAO() {
        super(UNIT_NAME);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ErrorCountI> getErrorCountBy10Min(int complexPkey, BpInterface bpInterface) {
        List<ErrorCount10Min> resultList = execNativeQueryList("call bpl.db_system_GetErrCnt2hr(?,?,?)",
                ErrorCount10Min.class,
                complexPkey, bpInterface.getMain(), bpInterface.getPart());
        for (ErrorCount10Min count : resultList) {
            count.setBusiness(count.getBusiness() - count.get499Code());
        }
        return new ArrayList<ErrorCountI>(resultList);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ErrorCountI> getErrorCountByHour(int complexPkey, LocalDate date, BpInterface bpInterface) {
        List<ErrorCountHour> resultList = execNativeQueryList("call bpl.db_system_GetErrCntByHr(?,?,?,?)",
                ErrorCountHour.class,
                complexPkey, date.toString(ERROR_COUNT_DATE_PATTERN), bpInterface.getMain(), bpInterface.getPart());
        for (ErrorCountHour count : resultList) {
            count.setDate(date);
            count.setBusiness(count.getBusiness() - count.get499Code());
        }
        return new ArrayList<ErrorCountI>(resultList);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ErrorCountI> getErrorCountByDay(int complexPkey, LocalDate startDate, LocalDate endDate, BpInterface bpInterface) {
        List<ErrorCountDay> resultList = execNativeQueryList("call bpl.db_system_GetErrCntByDay(?,?,?,?,?)",
                ErrorCountDay.class,
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
    @SuppressWarnings("unchecked")
    public List<ErrorCountI> getErrorCount6Month(int complexPkey, BpInterface bpInterface) {
        List<ErrorCount6Month> resultList = execNativeQueryList("call bpl.db_system_GetErrCnt6Month(?,?,?)",
                ErrorCount6Month.class,
                complexPkey,
                bpInterface.getMain(),
                bpInterface.getPart());
        for (ErrorCount6Month count : resultList) {
            count.setBusiness(count.getBusiness() - count.get499Code());
        }
        return new ArrayList<ErrorCountI>(resultList);
    }
}