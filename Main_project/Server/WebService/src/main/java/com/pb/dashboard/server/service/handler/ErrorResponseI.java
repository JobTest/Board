package com.pb.dashboard.server.service.handler;

/**
 * Created by vlad
 * Date: 06.05.15_11:47
 */
public interface ErrorResponseI {

    public String getRequestUrl();

    public int getCode();

    public String getMessage();
}
