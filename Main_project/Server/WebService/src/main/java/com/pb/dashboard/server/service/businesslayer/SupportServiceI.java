package com.pb.dashboard.server.service.businesslayer;

import com.pb.dashboard.server.dao.entity.iqlogrep.InterfaceErrorCountI;
import com.pb.dashboard.server.service.api.ErrorCountApiI;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by vlad
 * Date: 05.05.15_16:57
 */
public interface SupportServiceI {

    List<ErrorCountApiI> getErrorCountBy10Min(int complexPkey, String bpInterface);

    List<ErrorCountApiI> getErrorCountByHour(int complexPkey, LocalDate date, String bpInterface);

    List<ErrorCountApiI> getErrorCountByDay(int complexPkey, LocalDate startDate, LocalDate endDate, String bpInterface);

    List<ErrorCountApiI> getErrorCount6Month(int complexPkey, String bpInterface);

    List<InterfaceErrorCountI> getInterfaceErrorCounts(int complexId, boolean systemError, LocalDate date, Integer hour, Integer minute);
}
