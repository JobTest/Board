package com.pb.dashboard.monitoring.timings.transferlink;

import com.pb.dashboard.core.model.Complex;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;

public class SpecLink extends Link {
    public SpecLink(Complex complex) {
        setCaption("Спецификация");
        if (complex != null) {
            setResource(new ExternalResource(complex.getSpecLink()));
        }
    }
}
