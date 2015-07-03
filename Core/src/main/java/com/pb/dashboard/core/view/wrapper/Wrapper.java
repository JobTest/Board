package com.pb.dashboard.core.view.wrapper;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

/**
 * Created by vlad
 * Date: 16.01.15_14:09
 */
public class Wrapper {

    public static <T extends Component> Component wrap(WrapperSide side, T... components) {
        return wrap(side, Alignment.TOP_CENTER, components);
    }

    public static <T extends Component> Component wrap(WrapperSide side, Alignment align, T... components) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setMargin(new MarginInfo(side.getCode()));
        for (T component : components) {
            if (component != null) {
                layout.addComponent(component);
                layout.setComponentAlignment(component, align);
            }
        }
        return layout;
    }
}
