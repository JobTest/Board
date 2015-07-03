package com.pb.dashboard.dao.entity.vitrinametrics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by vlad
 * Date: 24.03.15_13:38
 */

@Entity
public class SlaInterface implements SlaInterfaceI, Serializable {

    private static final long serialVersionUID = 5034144745786741608L;
    @Id
    @Column(name = "pkey")
    private int pkey;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;

    @Override
    public int getPkey() {
        return pkey;
    }

    public void setPkey(int pkey) {
        this.pkey = pkey;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
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
        final SlaInterface other = (SlaInterface) obj;
        return Objects.equals(this.pkey, other.pkey) && Objects.equals(this.name, other.name) && Objects.equals(this.description, other.description);
    }
}
