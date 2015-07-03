package com.pb.dashboard.core.component;

import com.pb.dashboard.core.component.namemapping.ComponentNameI;
import com.vaadin.ui.TextField;

/**
 * Created by evasive on 12.11.14.
 */
public class DTextField extends TextField{

    public DTextField(ComponentNameI mappingComponents) {
        setId(mappingComponents.getName());
    }

    public DTextField(String caption, ComponentNameI componentNameI) {
        super(caption);
        setId(componentNameI.getName());
    }
}
