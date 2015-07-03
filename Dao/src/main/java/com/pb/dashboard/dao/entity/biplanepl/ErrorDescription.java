package com.pb.dashboard.dao.entity.biplanepl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by vlad
 * Date: 20.05.15_11:05
 */
@Entity
public class ErrorDescription implements ErrorDescriptionI, Serializable {

    private static final long serialVersionUID = 1551551526395571434L;

    @Id
    @Column(name = "text_ru")
    private String textRu;

    @Override
    public String getTextRu() {
        return textRu;
    }

    public ErrorDescription() {
    }

    public ErrorDescription(String textRu) {
        this.textRu = textRu;
    }

    public void setTextRu(String textRu) {
        this.textRu = textRu;
    }

    @Override
    public int hashCode() {
        return Objects.hash(textRu);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ErrorDescription other = (ErrorDescription) obj;
        return Objects.equals(this.textRu, other.textRu);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ErrorDescription{");
        sb.append("textRu='").append(textRu).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
