package com.pb.dashboard.monitoring.errors.all.db.container;

public class InfoError {

    private String textRu;
    private String reason;
    private String recommend;
    private String gr;
    private String responsible;
    private String system;
    private String type;

    public InfoError() {
    }

    public InfoError(String textRu, String reason, String recommend, String gr, String responsible, String system, String type) {
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
    public String toString() {
        return "InfoCodeData{" +
                "textRu='" + textRu + '\'' +
                ", gr='" + gr + '\'' +
                ", responsible='" + responsible + '\'' +
                ", system='" + system + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
