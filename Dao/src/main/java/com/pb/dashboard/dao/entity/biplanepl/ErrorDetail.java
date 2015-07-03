package com.pb.dashboard.dao.entity.biplanepl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class ErrorDetail implements ErrorDetailI, Serializable {

    private static final long serialVersionUID = -723818480294526929L;
    @Id
    @Column(name = "text_ru")
    private String textRu;
    @Id
    @Column(name = "reason")
    private String reason;
    @Id
    @Column(name = "recommend")
    private String recommend;
    @Id
    @Column(name = "gr")
    private String gr;
    @Id
    @Column(name = "responsible")
    private String responsible;
    @Id
    @Column(name = "system")
    private String system;
    @Id
    @Column(name = "type")
    private String type;

    public ErrorDetail() {
    }

    public ErrorDetail(String textRu, String reason, String recommend, String gr, String responsible, String system, String type) {
        this.textRu = textRu;
        this.reason = reason;
        this.recommend = recommend;
        this.gr = gr;
        this.responsible = responsible;
        this.system = system;
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getTextRu() {
        return textRu;
    }

    public void setTextRu(String textRu) {
        this.textRu = textRu;
    }

    public String getGr() {
        return gr;
    }

    public void setGr(String gr) {
        this.gr = gr;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(textRu, reason, recommend, gr, responsible, system, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ErrorDetail other = (ErrorDetail) obj;
        return Objects.equals(this.textRu, other.textRu) && Objects.equals(this.reason, other.reason) && Objects.equals(this.recommend, other.recommend) && Objects.equals(this.gr, other.gr) && Objects.equals(this.responsible, other.responsible) && Objects.equals(this.system, other.system) && Objects.equals(this.type, other.type);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ErrorInfo{");
        sb.append("textRu='").append(textRu).append('\'');
        sb.append(", reason='").append(reason).append('\'');
        sb.append(", recommend='").append(recommend).append('\'');
        sb.append(", gr='").append(gr).append('\'');
        sb.append(", responsible='").append(responsible).append('\'');
        sb.append(", system='").append(system).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
