package com.pb.dashboard.dao.entity.vitrinametrics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by vlad
 * Date: 06.04.15_14:58
 */
@Entity
public class DInterface implements DInterfaceI, Serializable {

    private static final long serialVersionUID = -6409419903674606698L;
    @Id
    @Column(name = "pkey_int")
    private Integer pkey;
    @Column(name = "int_name")
    private String name;
    @Column(name = "description")
    private String description;

    public DInterface() {
    }

    public DInterface(Integer pkey, String name, String description) {
        this.pkey = pkey;
        this.name = name;
        this.description = description;
    }

    public DInterface(Integer pkey) {
        this.pkey = pkey;
    }

    public Integer getPkey() {
        return pkey;
    }

    public void setPkey(Integer pkey) {
        this.pkey = pkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkey, name, description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DInterface other = (DInterface) obj;
        return Objects.equals(this.pkey, other.pkey) && Objects.equals(this.name, other.name) && Objects.equals(this.description, other.description);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DInterface{");
        sb.append("pkey=").append(pkey);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
