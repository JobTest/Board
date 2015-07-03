package com.pb.dashboard.itdashbord.errors.db.container;

public class ErrorsData {

    private String id;
    private String name;
    private String branch;
    private String okpo;
    private String view;
    private String recipientType;
    private String groupType;
    private String countError;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setOkpo(String okpo) {
        this.okpo = okpo;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public void setCountError(String countError) {
        this.countError = countError;
    }

    public String[] getAsArray(){
        return new String[]{id, name, branch, okpo, view, recipientType, groupType, countError,type};
    }
}
