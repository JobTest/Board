package com.pb.dashboard.server.service.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by vlad
 * Date: 06.05.15_11:20
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponseI handleError(HttpServletRequest req, Exception e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), e.getMessage());
    }

}
