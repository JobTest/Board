package com.pb.dashboard.core.component;

import com.vaadin.event.MouseEvents;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import java.io.Serializable;

/**
 * Created by vlad
 * Date: 12.12.14_15:19
 */

public class DPopupWindow implements Serializable {

    private MouseEvents.ClickListener listener;
    private Window popup;
    private int x;
    private int y;

    public Window open(int x, int y, Component content) {
        create(x, y);
        setContent(content);
        UI.getCurrent().addWindow(popup);
        return popup;
    }

    private void create(int x, int y) {
        if (popup == null) {
            initWindow();
        } else {
            close();
        }

        this.x = x;
        this.y = y;
        initListener();

        popup.setPositionX(x);
        popup.setPositionY(y);

        UI.getCurrent().addClickListener(listener);
    }

    private void initWindow() {
        popup = new Window();
        popup.addStyleName("popup");

        popup.setDraggable(false);
        popup.setResizable(false);
        popup.setClosable(false);

        popup.addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent clickEvent) {
                close();
                UI.getCurrent().removeClickListener(listener);
            }
        });
    }

    public Window setContent(Component content) {
        if (popup == null) {
            open(x, y, content);
        } else {
            popup.setContent(content);
        }
        return popup;
    }

    private void initListener() {
        listener = new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent event) {
                UI.getCurrent().removeClickListener(this);
                close();
            }
        };
    }

    public void close() {
        if (popup != null) {
            popup.close();
        }
    }
}