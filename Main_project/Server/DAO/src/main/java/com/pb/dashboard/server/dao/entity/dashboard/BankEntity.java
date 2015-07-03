package com.pb.dashboard.server.dao.entity.dashboard;

import javax.persistence.*;

/**
 * Created by vlad
 * Date: 19.01.15_15:54
 */
@Entity
@Table(name = "bank", schema = "", catalog = "dashboard")
public class BankEntity {
    private int id;
    private String attrId;
    private String country;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "attr_id", nullable = false, insertable = true, updatable = true, length = 5)
    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    @Basic
    @Column(name = "country", nullable = false, insertable = true, updatable = true, length = 15)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankEntity that = (BankEntity) o;

        if (id != that.id) return false;
        if (attrId != null ? !attrId.equals(that.attrId) : that.attrId != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (attrId != null ? attrId.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BankEntity{");
        sb.append("id=").append(id);
        sb.append(", attrId='").append(attrId).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
