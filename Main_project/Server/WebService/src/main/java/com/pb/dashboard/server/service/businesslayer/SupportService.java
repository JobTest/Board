package com.pb.dashboard.server.service.businesslayer;

import com.pb.dashboard.server.dao.entity.iqlogrep.BpInterface;
import com.pb.dashboard.server.dao.entity.iqlogrep.ErrorCountI;
import com.pb.dashboard.server.dao.entity.iqlogrep.InterfaceErrorCountI;
import com.pb.dashboard.server.dao.service.DaoFactoryI;
import com.pb.dashboard.server.service.api.ErrorCountApiI;
import com.pb.dashboard.server.service.converter.SupportConverter;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by vlad
 * Date: 05.05.15_17:22
 */
public class SupportService implements SupportServiceI {

    public static final String BIPLANE = "biplane";
    public static final String EXTERNAL = "external";

    private DaoFactoryI dao;

    @Override
    public List<ErrorCountApiI> getErrorCountBy10Min(int complexPkey, String bpInterface) {
        validComplex(complexPkey);
        return SupportConverter.convert(dao.getIq().getErrorCountBy10Min(complexPkey, splitByInterfaceName(bpInterface)));
    }

    @Override
    public List<ErrorCountApiI> getErrorCountByHour(int complexPkey, LocalDate date, String bpInterface) {
        validComplex(complexPkey);
        List<ErrorCountI> list = dao.getIq().getErrorCountByHour(complexPkey, date, splitByInterfaceName(bpInterface));
        return SupportConverter.convert(list);
    }

    @Override
    public List<ErrorCountApiI> getErrorCountByDay(int complexPkey, LocalDate startDate, LocalDate endDate, String bpInterface) {
        validComplex(complexPkey);
        if (!datesInSameMonth(startDate, endDate)) {
            throw new IllegalArgumentException("Даты могут быть только внутри одного месяца!");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Начальный день не может быть больше конечного!");
        }
        return SupportConverter.convert(dao.getIq().getErrorCountByDay(complexPkey, startDate, endDate, splitByInterfaceName(bpInterface)));
    }

    private boolean datesInSameMonth(LocalDate d1, LocalDate d2) {
        if (d1.getYear() != d2.getYear()) {
            return false;
        }
        if (d1.getMonthOfYear() != d2.getMonthOfYear()) {
            return false;
        }
        return true;
    }

    @Override
    public List<ErrorCountApiI> getErrorCount6Month(int complexPkey, String bpInterface) {
        validComplex(complexPkey);
        return SupportConverter.convert(dao.getIq().getErrorCount6Month(complexPkey, splitByInterfaceName(bpInterface)));
    }

    @Override
    public List<InterfaceErrorCountI> getInterfaceErrorCounts(int complexPkey, boolean systemError, LocalDate date, Integer hour, Integer minute) {
        validComplex(complexPkey);
        int isSystem = systemError ? 1 : 0;
        int year = date.getYear();
        int month = date.getMonthOfYear();
        int day = date.getDayOfMonth();
        return dao.getIq().getInterfaceErrorCounts(complexPkey, isSystem, year, month, day, hour, minute);
    }

    private void validComplex(int complexId) {
        if (complexId != 1 && complexId != 5) {
            throw new IllegalArgumentException("Complex id incorrect. Must 1 or 5.");
        }
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

    public void setServiceFactory(DaoFactoryI serviceFactory) {
        this.dao = serviceFactory;
    }
}
