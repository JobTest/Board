package com.pb.dashboard.dao.entity.biplanesupport.db;

/**
 * Created by vlad
 * Date: 29.04.15_16:56
 */
public class BpSession {

    private String id;
    private Integer duration;
    private SessionType sessionType = SessionType.MAX_OK;

    public BpSession(String id) {
        this.id = id;
    }

    public BpSession(String id, int duration, int sessionId) {
        this.id = id;
        this.duration = duration;
        try {
            this.sessionType = SessionType.idToType(sessionId);
        } catch (Exception ignore) {
        }
    }

    public String getId() {
        return id;
    }

    public Integer getDuration() {
        return duration;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

}
