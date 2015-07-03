package com.pb.dashboard.dao.entity.vitrinametrics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by vlad
 * Date: 06.04.15_14:21
 */
@Entity
public class InterfaceMetric implements InterfaceMetricI, Serializable {

    private static final long serialVersionUID = 649565233346473530L;
    @Id
    @Column(name = "pkey")
    private int pkey;
    @Column(name = "description")
    private String description;
    @Column(name = "result_set_fields")
    private int mainMetrics;

    public InterfaceMetric() {
    }

    public InterfaceMetric(String description) {
        this.description = description;
    }

    public InterfaceMetric(int pkey) {
        this.pkey = pkey;
    }

    public InterfaceMetric(int pkey, String description, int mainMetrics) {
        this.pkey = pkey;
        this.description = description;
        this.mainMetrics = mainMetrics;
    }

    public int getPkey() {
        return pkey;
    }

    public void setPkey(int pkey) {
        this.pkey = pkey;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean isMain() {
        return mainMetrics == MAIN_METRICS;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMainMetrics() {
        return mainMetrics;
    }

    public void setMainMetrics(int mainMetrics) {
        this.mainMetrics = mainMetrics;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkey, description, mainMetrics);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final InterfaceMetric other = (InterfaceMetric) obj;
        return Objects.equals(this.pkey, other.pkey) && Objects.equals(this.description, other.description) && Objects.equals(this.mainMetrics, other.mainMetrics);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InterfaceMetrics{");
        sb.append("pkey=").append(pkey);
        sb.append(", description='").append(description).append('\'');
        sb.append(", mainMetrics=").append(mainMetrics);
        sb.append('}');
        return sb.toString();
    }
}
