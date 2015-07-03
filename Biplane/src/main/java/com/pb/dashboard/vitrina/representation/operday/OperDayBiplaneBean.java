package com.pb.dashboard.vitrina.representation.operday;

import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.payment.data.Metrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OperDayBiplaneBean implements Serializable {

    private List<Metrics> noneHourMetrics;
    private ASEDBManager dm = ASEDBManager.getInstance();
    private int opened;
    private int closed;
    private int openedkassaAll;
    private int closedkassaAll;
    private int openedbassAll;
    private int closedbassaAll;
    private int openedp24All;
    private int closedp24All;

    public List<Object[]> getOperListKassa() {
        fillLists();
        openedbassAll = 0;
        openedkassaAll = 0;
        List<Object[]> operListKassa = new ArrayList<Object[]>();
        getMetric(4, 5);
        operListKassa.add(new Object[]{"Операционное время", String.valueOf(opened), String.valueOf(closed)});
        openedkassaAll += opened;
        closedkassaAll += closed;
        getMetric(6, 7);
        operListKassa.add(new Object[]{"Послеоперационное время", String.valueOf(opened), String.valueOf(closed)});
        openedkassaAll += opened;
        closedkassaAll += closed;
        getMetric(8, 9);
        operListKassa.add(new Object[]{"Опер. время с персональными счетами", String.valueOf(opened), String.valueOf(0)});
        getMetric(9, 8);
        operListKassa.add(new Object[]{"Послеопер. время с с персональными счетами", String.valueOf(opened), String.valueOf(0)});
        return operListKassa;
    }

    public List<Object[]> getOperListBass() {
        closedbassaAll = 0;
        closedbassaAll = 0;
        List<Object[]> operListBass = new ArrayList<Object[]>();
        getMetric(13, 14);
        operListBass.add(new Object[]{"Операционное время", String.valueOf(opened), String.valueOf(closed)});
        openedbassAll += opened;
        closedbassaAll += closed;
        getMetric(15, 16);
        operListBass.add(new Object[]{"Послеоперационное время", String.valueOf(opened), String.valueOf(closed)});
        openedbassAll += opened;
        closedbassaAll += closed;
        return operListBass;
    }

    public List<Object[]> getOperListP24() {
        closedp24All = 0;
        openedp24All = 0;
        List<Object[]> operListP24 = new ArrayList<Object[]>();
        getMetric(19, 20);
        operListP24.add(new Object[]{"Операционное время", String.valueOf(opened), String.valueOf(closed)});
        openedp24All += opened;
        closedp24All += closed;
        getMetric(21, 22);
        operListP24.add(new Object[]{"Послеоперационное время", String.valueOf(opened), String.valueOf(closed)});
        openedp24All += opened;
        closedp24All += closed;
        return operListP24;
    }

    private void fillLists() {
        noneHourMetrics = dm.noneHourMetrics;
    }

    private void getMetric(int p1, int p2) {
        opened = 0;
        closed = 0;
        for (Metrics m : noneHourMetrics) {
            if (m.getMetricKey() == p1) {
                opened = m.getMetricValue();
            } else if (m.getMetricKey() == p2) {
                closed = m.getMetricValue();
            }
        }
    }

    public Object[] getKassaSum() {
        return new Object[]{"Всего", String.valueOf(openedkassaAll), String.valueOf(closedkassaAll)};
    }

    public Object[] getBassSum() {
        return new Object[]{"Всего", String.valueOf(openedbassAll), String.valueOf(closedbassaAll)};
    }

    public Object[] getP24Sum() {
        return new Object[]{"Всего", String.valueOf(openedp24All), String.valueOf(closedp24All)};
    }
}
