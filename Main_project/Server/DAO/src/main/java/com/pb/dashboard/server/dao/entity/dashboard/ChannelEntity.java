package com.pb.dashboard.server.dao.entity.dashboard;

import javax.persistence.*;

/**
 * Created by vlad
 * Date: 19.01.15_15:54
 */
@Entity
@Table(name = "channel", schema = "", catalog = "dashboard")
public class ChannelEntity {
    private int id;
    private String attrId;
    private String name;
    private Integer fkeyChannelOutput;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "attr_id", nullable = false, insertable = true, updatable = true, length = 15)
    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "fkey_channel_output", nullable = true, insertable = true, updatable = true)
    public Integer getFkeyChannelOutput() {
        return fkeyChannelOutput;
    }

    public void setFkeyChannelOutput(Integer fkeyChannelOutput) {
        this.fkeyChannelOutput = fkeyChannelOutput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChannelEntity that = (ChannelEntity) o;

        if (id != that.id) return false;
        if (attrId != null ? !attrId.equals(that.attrId) : that.attrId != null) return false;
        if (fkeyChannelOutput != null ? !fkeyChannelOutput.equals(that.fkeyChannelOutput) : that.fkeyChannelOutput != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (attrId != null ? attrId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (fkeyChannelOutput != null ? fkeyChannelOutput.hashCode() : 0);
        return result;
    }
}
