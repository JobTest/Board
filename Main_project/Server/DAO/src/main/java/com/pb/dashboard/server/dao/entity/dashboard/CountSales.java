package com.pb.dashboard.server.dao.entity.dashboard;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by vlad
 * Date: 21.01.15_13:19
 */

@Entity
public class CountSales {

    private int channelId;
    private int countSales;

    @Id
    @Column(name = "id")
    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @Basic
    @Column(name = "value")
    public int getCountSales() {
        return countSales;
    }

    public void setCountSales(int countSales) {
        this.countSales = countSales;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CountSales{");
        sb.append("channelId=").append(channelId);
        sb.append(", countSales=").append(countSales);
        sb.append('}');
        return sb.toString();
    }
}
