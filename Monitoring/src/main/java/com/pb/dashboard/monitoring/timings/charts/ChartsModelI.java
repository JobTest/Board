package com.pb.dashboard.monitoring.timings.charts;

import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.timings.sla.SlaModelI;
import com.pb.dashboard.monitoring.timings.tabsheet.IndicatorsModelI;

/**
 * Created by vlad
 * Date: 12.12.14_15:09
 */
public interface ChartsModelI {

    boolean isLogarithmic();

    void setLogarithmic(boolean logarithmic);

    IndicatorsModelI getIndicatorsModel();

    void setIndicatorsModel(IndicatorsModelI indicatorsModel);

    void notifyAllObservers();

    void addObserver(Observer<ChartsModelI> observer);

    void removeObserver(Observer<ChartsModelI> observer);

    void setSlaModel(SlaModelI model);

    SlaModelI getSlaModel();
}
