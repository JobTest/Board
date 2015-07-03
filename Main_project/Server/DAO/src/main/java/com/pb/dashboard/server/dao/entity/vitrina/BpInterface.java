package com.pb.dashboard.server.dao.entity.vitrina;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by vlad
 * Date: 23.01.15_16:04
 */
@Entity
public class BpInterface implements BpInterfaceI, Serializable {

    private static final long serialVersionUID = 3150077205209530912L;
    @Id
    @Column(name = "pkey_int")
    private Integer pkey;
    @Column(name = "int_name")
    private String name;
    @Column(name = "description")
    private String description;

    public Integer getPkey() {
        return pkey;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setPkey(Integer pkey) {
        this.pkey = pkey;
    }

    public void setName(String name) {
        this.name = name;
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
        final BpInterface other = (BpInterface) obj;
        return Objects.equals(this.pkey, other.pkey)
                && Objects.equals(this.name, other.name)
                && Objects.equals(this.description, other.description);
    }
}
