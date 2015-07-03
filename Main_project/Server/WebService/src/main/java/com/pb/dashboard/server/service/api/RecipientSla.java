package com.pb.dashboard.server.service.api;

import com.pb.dashboard.server.dao.entity.description.DescriptionCompany;
import com.pb.dashboard.server.dao.entity.iqlogrep.RecipientEntity;

/**
 * Created by vlad
 * Date: 05.03.15_14:05
 */
public class RecipientSla {

    private int id;
    private String name;
    private String filial;
    private int count;
    private int errorCount;
    private int durationMax;
    private int duration90;
    private int duration95;
    private int duration99;

    public RecipientSla(RecipientEntity item, DescriptionCompany description) {
        this.id = item.getId();
        this.name = description.getName();
        this.filial = description.getFilial();
        this.count = item.getCount();
        this.errorCount = item.getErrorCount();
        this.durationMax = item.getDurationMax();
        this.duration90 = item.getDuration90();
        this.duration95 = item.getDuration95();
        this.duration99 = item.getDuration99();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFilial() {
        return filial;
    }

    public int getCount() {
        return count;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public int getDurationMax() {
        return durationMax;
    }

    public int getDuration90() {
        return duration90;
    }

    public int getDuration95() {
        return duration95;
    }

    public int getDuration99() {
        return duration99;
    }
}
