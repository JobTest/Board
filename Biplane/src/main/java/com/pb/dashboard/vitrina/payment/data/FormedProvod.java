package com.pb.dashboard.vitrina.payment.data;

import java.io.Serializable;

public class FormedProvod implements Serializable {
    private String unloaded;
    private String held;
    private String pending;
    private String notUnloaded;
    private String entriesLic;
    private String all;

    public FormedProvod() {
    }

    public FormedProvod(String unloaded, String held, String pending, String notUnloaded, String entriesLic, String all) {
        this.unloaded = unloaded;
        this.held = held;
        this.pending = pending;
        this.notUnloaded = notUnloaded;
        this.entriesLic = entriesLic;
        this.all = all;
    }

    public String getAll() {
        return all;
    }

    public String getEntriesLic() {
        return entriesLic;
    }

    public String getHeld() {
        return held;
    }

    public String getNotUnloaded() {
        return notUnloaded;
    }

    public String getPending() {
        return pending;
    }

    public String getUnloaded() {
        return unloaded;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public void setEntriesLic(String entriesLic) {
        this.entriesLic = entriesLic;
    }

    public void setHeld(String held) {
        this.held = held;
    }

    public void setNotUnloaded(String notUnloaded) {
        this.notUnloaded = notUnloaded;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public void setUnloaded(String unloaded) {
        this.unloaded = unloaded;
    }

    @Override
    public String toString() {
        return "FormedProvod{" + "unloaded=" + unloaded + ", held=" + held + ", pending=" + pending + ", notUnloaded=" + notUnloaded + ", entriesLic=" + entriesLic + ", all=" + all + '}';
    }


}
