package com.pb.dashboard.monitoring.errors.all.window;

import com.pb.dashboard.core.model.Complex;

public class SessionModel {

    private String session;
    private Complex complex;

    public void setSession(String session) {
        this.session = session;
    }

    public String getSession() {
        return session;
    }

    public Complex getComplex() {
        return complex;
    }

    public void setComplex(Complex complex) {
        this.complex = complex;
    }
}
