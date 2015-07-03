package com.pb.dashboard.monitoring.timings.charts;

import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.components.observers.Observers;
import com.pb.dashboard.monitoring.timings.sla.SlaModelI;
import com.pb.dashboard.monitoring.timings.tabsheet.IndicatorsModelI;

/**
 * Created by vlad
 * Date: 15.12.14_9:12
 */
public class ChartsModel implements ChartsModelI {

    private Observers<Observer, ChartsModelI> observers = new Observers<Observer, ChartsModelI>();

    private IndicatorsModelI indicatorsModel;
    private SlaModelI slaModel;
    private boolean logarithmic;

    public IndicatorsModelI getIndicatorsModel() {
        return indicatorsModel;
    }

    public void setIndicatorsModel(IndicatorsModelI indicatorsModel) {
        this.indicatorsModel = indicatorsModel;
    }

    @Override
    public void notifyAllObservers() {
        observers.notifyModified(this);
    }

    @Override
    public boolean isLogarithmic() {
        return logarithmic;
    }

    @Override
    public void setLogarithmic(boolean logarithmic) {
        this.logarithmic = logarithmic;
    }

    @Override
    public void addObserver(Observer<ChartsModelI> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<ChartsModelI> observer) {
        observers.remove(observer);
    }

    @Override
    public void setSlaModel(SlaModelI model) {
        this.slaModel = model;
    }

    @Override
    public SlaModelI getSlaModel() {
        return slaModel;
    }
}
