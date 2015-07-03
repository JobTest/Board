package com.pb.dashboard.dao.entity.vitrinametrics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by vlad
 * Date: 06.04.15_16:04
 */

@Entity
public class InterfaceLimit implements InterfaceLimitI, Serializable {

    private static final long serialVersionUID = -216762521193625333L;
    @Id
    @Column(name = "warning_limit")
    private int warning;
    @Column(name = "critical_limit")
    private int critical;

    public InterfaceLimit() {
    }

    public InterfaceLimit(int warning, int critical) {
        this.warning = warning;
        this.critical = critical;
    }

    @Override
    public int getWarning() {
        return warning;
    }

    public void setWarning(int warning) {
        this.warning = warning;
    }

    @Override
    public int getCritical() {
        return critical;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    @Override
    public int hashCode() {
        return Objects.hash(warning, critical);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final InterfaceLimit other = (InterfaceLimit) obj;
        return Objects.equals(this.warning, other.warning) && Objects.equals(this.critical, other.critical);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InterfaceLimit{");
        sb.append("warning=").append(warning);
        sb.append(", critical=").append(critical);
        sb.append('}');
        return sb.toString();
    }
}