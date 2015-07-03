package com.pb.dashboard.monitoring.components.navigator;

/**
 * Created by vlad
 * Date: 24.11.14_9:48
 */
public class ContentItem {

    private String pkey;
    private String name;

    public ContentItem() {
    }

    public ContentItem(String pkey, String name) {
        this.pkey = pkey;
        this.name = name;
    }

    public ContentItem(Integer pkey, String name) {
        this.pkey = String.valueOf(pkey);
        this.name = name;
    }

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
