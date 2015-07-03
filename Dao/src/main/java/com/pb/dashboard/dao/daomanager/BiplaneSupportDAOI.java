package com.pb.dashboard.dao.daomanager;

import com.pb.dashboard.dao.entity.biplanesupport.ErrorCountI;
import com.pb.dashboard.dao.service.container.BpInterface;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by vlad
 * Date: 01.04.15_12:32
 */
public interface BiplaneSupportDAOI {

    List<ErrorCountI> getErrorCountBy10Min(int complexPkey, BpInterface bpInterface);

    List<ErrorCountI> getErrorCountByHour(int complexPkey, LocalDate date, BpInterface bpInterface);

    List<ErrorCountI> getErrorCountByDay(int complexPkey, LocalDate startDate, LocalDate endDate, BpInterface bpInterface);

    List<ErrorCountI> getErrorCount6Month(int complexPkey, BpInterface bpInterface);

}
