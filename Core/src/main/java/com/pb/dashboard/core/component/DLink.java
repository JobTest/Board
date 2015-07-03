package com.pb.dashboard.core.component;

import com.pb.dashboard.core.component.namemapping.ComponentNameI;
import com.vaadin.ui.Link;

/**
 * Created by evasive on 11/12/14.
 */
public class DLink extends Link {
    public DLink(ComponentNameI mappingComponents) {
        setId(mappingComponents.getName());
    }

}
