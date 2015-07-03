package com.pb.dashboard.vitrina.statistics.byday;

import com.pb.dashboard.vitrina.core.config.ConfigManager;
import com.pb.dashboard.vitrina.payment.data.StatEnum;
import com.pb.dashboard.vitrina.statistics.Utilities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StatsDayBean implements Serializable {
    SelectionBean selectionBean = new SelectionBean();

    public void setConfigManager(ConfigManager manager) {
        selectionBean.setConfigManager(manager);
    }

    public void updateData(StatEnum stat) {
        selectionBean.collectByDate(new Date());
        selectionBean.updateByType(stat);
    }

    public List<Object[]> getPayment(StatEnum stat) {
        updateData(stat);
        return Utilities.convertToTableData(selectionBean.getMainData());
    }

    public Object[] getSumMetrics() {
        return new Object[]{"Всего", "", "", "", selectionBean.getSum() + "", "", ""};
    }

    public Object getSum() {
        return selectionBean.getSum() + "";
    }
}
