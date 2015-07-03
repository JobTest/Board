package com.pb.dashboard.monitoring.timings.sla;

import com.pb.dashboard.dao.entity.vitrinametrics.SlaCountI;
import com.pb.dashboard.dao.entity.vitrinametrics.SlaInterfaceI;
import com.pb.dashboard.dao.entity.vitrinametrics.SlaTimingI;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.timings.charts.reglament.DateModelI;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 06.11.14_15:01
 */
public interface SlaModelI extends DateModelI {

    public boolean isReglament();

    public void setReglament(boolean reglament);

    public Map<Integer, SlaInterfaceI> getSlaInterfaces();

    public void setSlaInterfaces(Map<Integer, SlaInterfaceI> slaInterfaces);

    public SlaInterfaceI getParent();

    public void setParent(SlaInterfaceI parent);

    public Map<SlaInterfaceI, List<SlaCountI>> getSlaCounts();

    public void setSlaCounts(Map<SlaInterfaceI, List<SlaCountI>> slaCounts);

    public Map<SlaInterfaceI, List<SlaTimingI>> getSlaTimings();

    public void setSlaTimings(Map<SlaInterfaceI, List<SlaTimingI>> slaTimings);

    public boolean isValidInputDate();

    public Integer getPkeyInterface();

    public void setPkeyInterface(Integer pkeyInterface);

    public FilterRange getFilterRange();

    public void setFilterRange(FilterRange filterRange);

    public LocalDate getDate();

    public void setDate(LocalDate date);

    public LocalDate getFromDate();

    public void setFromDate(LocalDate fromDate);

    public LocalDate getToDate();

    public void setToDate(LocalDate toDate);

    public void addObserver(Observer<SlaModelI> observer);

    public void notifyAllObservers();

}
