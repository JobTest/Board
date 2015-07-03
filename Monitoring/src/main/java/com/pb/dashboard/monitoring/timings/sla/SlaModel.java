package com.pb.dashboard.monitoring.timings.sla;

import com.pb.dashboard.dao.entity.vitrinametrics.SlaCountI;
import com.pb.dashboard.dao.entity.vitrinametrics.SlaInterfaceI;
import com.pb.dashboard.dao.entity.vitrinametrics.SlaTimingI;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.components.observers.Observers;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 06.11.14_15:00
 */
public class SlaModel implements SlaModelI {

    private Observers<Observer, SlaModelI> observers = new Observers<>();
    private Integer pkeyInterface;
    private FilterRange filterRange;
    private LocalDate date;
    private LocalDate fromDate;
    private LocalDate toDate;
    private boolean reglament = false;

    private SlaInterfaceI parent;
    private Map<Integer, SlaInterfaceI> slaInterfaces;
    private Map<SlaInterfaceI, List<SlaCountI>> slaCounts;
    private Map<SlaInterfaceI, List<SlaTimingI>> slaTimings;

    public boolean isReglament() {
        return reglament;
    }

    public void setReglament(boolean reglament) {
        this.reglament = reglament;
    }

    public Map<Integer, SlaInterfaceI> getSlaInterfaces() {
        return slaInterfaces;
    }

    public void setSlaInterfaces(Map<Integer, SlaInterfaceI> slaInterfaces) {
        this.slaInterfaces = slaInterfaces;
    }

    public SlaInterfaceI getParent() {
        return parent;
    }

    public void setParent(SlaInterfaceI parent) {
        this.parent = parent;
    }

    public boolean isValidInputDate() {
        if (pkeyInterface == null || filterRange == null) {
            return false;
        }
        switch (filterRange) {
            case R10MIN:
            case HOUR:
                if (date == null) {
                    return false;
                }
                break;
            case DAY:
                if (fromDate == null || toDate == null) {
                    return false;
                }
        }
        return true;
    }

    public Map<SlaInterfaceI, List<SlaCountI>> getSlaCounts() {
        return slaCounts;
    }

    public void setSlaCounts(Map<SlaInterfaceI, List<SlaCountI>> slaCounts) {
        this.slaCounts = slaCounts;
    }

    public Map<SlaInterfaceI, List<SlaTimingI>> getSlaTimings() {
        return slaTimings;
    }

    public void setSlaTimings(Map<SlaInterfaceI, List<SlaTimingI>> slaTimings) {
        this.slaTimings = slaTimings;
    }

    public Integer getPkeyInterface() {
        return pkeyInterface;
    }

    public void setPkeyInterface(Integer pkeyInterface) {
        this.pkeyInterface = pkeyInterface;
    }

    public FilterRange getFilterRange() {
        return filterRange;
    }

    public void setFilterRange(FilterRange filterRange) {
        this.filterRange = filterRange;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public void addObserver(Observer<SlaModelI> observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        observers.notifyModified(this);
    }
}
