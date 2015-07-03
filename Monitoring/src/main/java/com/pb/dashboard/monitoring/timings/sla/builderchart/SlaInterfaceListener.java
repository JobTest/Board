package com.pb.dashboard.monitoring.timings.sla.builderchart;

import com.pb.dashboard.dao.entity.vitrinametrics.SlaInterfaceI;

/**
 * Created by vlad
 * Date: 27.03.15_14:59
 */
public interface SlaInterfaceListener {

    public void change(SlaInterfaceI slaInterface);
}
