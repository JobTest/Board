package com.pb.dashboard.dao.entity.biplanesupport.db;

public class SessionErrorsData {

    private String loggerName;
    private String interfaceName;
    private String startTime;
    private String module;
    private String errorText;
    private String clientErrorCode;
    private String textMessage;
    private String serviceId;
    private String alias;
    private String clientIpAddress;

    public SessionErrorsData() {
    }

    public SessionErrorsData(String loggerName, String interfaceName, String startTime, String module,
                             String errorText, String clientErrorCode, String textMessage, String serviceId,
                             String alias, String clientIpAddress) {
        this.loggerName = loggerName;
        this.interfaceName = interfaceName;
        this.startTime = startTime;
        this.module = module;
        this.errorText = errorText;
        this.clientErrorCode = clientErrorCode;
        this.textMessage = textMessage;
        this.serviceId = serviceId;
        this.alias = alias;
        this.clientIpAddress = clientIpAddress;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public String getClientErrorCode() {
        return clientErrorCode;
    }

    public void setClientErrorCode(String clientErrorCode) {
        this.clientErrorCode = clientErrorCode;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }
}
