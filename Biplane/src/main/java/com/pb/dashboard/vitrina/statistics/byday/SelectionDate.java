package com.pb.dashboard.vitrina.statistics.byday;

import com.pb.dashboard.vitrina.payment.data.StatEnum;
import com.pb.dashboard.vitrina.statistics.Utilities;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vic_n
 * Date: 12/17/13
 * Time: 6:10 PM
 */

public class SelectionDate {

    private Date date;
    private Map<StatEnum, List<Integer[]>> allTypes;
    private Integer[] totalsByType = new Integer[Utilities.types.length];
    private List<Integer[]> data;
    private int total;

    public SelectionDate(Date date, Map<StatEnum, List<Integer[]>> allTypes, Integer[] totalsByType) {
        this.date = date;
        this.allTypes = allTypes;
        this.totalsByType = totalsByType;
    }

    public Date getDate() {
        return date;
    }

    public Map<StatEnum, List<Integer[]>> getAllTypes() {
        return allTypes;
    }

    public List<Integer[]> getData() {
        return data;
    }

    public void setData(List<Integer[]> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setByType(StatEnum type) {
        if (type == StatEnum.ALL) data = Utilities.mergeAllTypes(allTypes);
        else data = allTypes.get(type);
        setTotalByType(type);
    }

    private void setTotalByType(StatEnum type) {
        if (type == StatEnum.KASSA) total = totalsByType[0];
        else if (type == StatEnum.BASS) total = totalsByType[1];
        else if (type == StatEnum.P24) total = totalsByType[2];
        else if (type == StatEnum.P3700) total = totalsByType[3];
        else total = totalsByType[0] + totalsByType[1] + totalsByType[2] + totalsByType[3];
    }

}
