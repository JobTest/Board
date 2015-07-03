package com.pb.dashboard.server.dao.entity.iqlogrep;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by vlad
 * Date: 19.05.15_11:53
 */
@Entity
public class InterfaceErrorCount implements InterfaceErrorCountI, Serializable {

    private static final long serialVersionUID = 2478721166648292731L;
    @Id
    @Column(name = "interface")
    private String interfaceName;
    @Column(name = "err_cnt")
    private int errorCount;

    public InterfaceErrorCount() {
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Override
    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(interfaceName, errorCount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final InterfaceErrorCount other = (InterfaceErrorCount) obj;
        return Objects.equals(this.interfaceName, other.interfaceName) && Objects.equals(this.errorCount, other.errorCount);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InterfaceErrorCount{");
        sb.append("interfaceName='").append(interfaceName).append('\'');
        sb.append(", errorCount=").append(errorCount);
        sb.append('}');
        return sb.toString();
    }
}
