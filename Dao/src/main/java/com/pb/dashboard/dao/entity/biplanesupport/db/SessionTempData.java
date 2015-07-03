package com.pb.dashboard.dao.entity.biplanesupport.db;

/**
 * Created by vlad
 * Date: 22.09.14
 */
public class SessionTempData extends DObject {

    private String interfaceName;
    private String module;
    private String errorCode;
    private String status;
    private String httpStatus;
    private String loggerName;
    private String startTime;
    private String duration;
    private String method;
    private String dopInfo;
    private String clientErrorCode;

    public SessionTempData() {
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDopInfo() {
        return dopInfo;
    }

    public void setDopInfo(String dopInfo) {
        this.dopInfo = dopInfo;
    }

    public String getClientErrorCode() {
        return clientErrorCode;
    }

    public void setClientErrorCode(String clientErrorCode) {
        this.clientErrorCode = clientErrorCode;
    }
}
