package com.pb.dashboard.server.service.handler;

/**
 * Created by vlad
 * Date: 06.05.15_11:20
 */
public class ErrorResponse implements ErrorResponseI {

    private int code;
    private String requestUrl;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(int code, String requestUrl, String message) {
        this.code = code;
        this.requestUrl = requestUrl;
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}