package com.pb.dashboard.server.dao.service;

import com.pb.dashboard.server.dao.entity.iqlogrep.*;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad
 * Date: 05.03.15_13:52
 */
public interface IQDaoI {

    public List<RecipientEntity> getDebtSlaByCompany(String interfaceName, LocalDate date, int top);

    public List<CountForCompany> getDebtErrByCompanyHour(String interfaceName, LocalDate date, int companyId);

    public List<ErrorCountI> getErrorCountBy10Min(int complexPkey, BpInterface bpInterface);

    public List<ErrorCountI> getErrorCountByHour(int complexPkey, LocalDate date, BpInterface bpInterface);

    public List<ErrorCountI> getErrorCountByDay(int complexPkey, LocalDate startDate, LocalDate endDate, BpInterface bpInterface);

    public List<ErrorCountI> getErrorCount6Month(int complexPkey, BpInterface bpInterface);

    public List<InterfaceErrorCountI> getInterfaceErrorCounts(int complexPkey, int system, int year, int month, int day, Integer hour, Integer minute);

}
