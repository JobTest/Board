package com.pb.dashboard.core.db;

import com.pb.dashboard.core.error.DashSQLException;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

/**
 * Created by vlad
 * Date: 25.12.14_14:12
 */
public class DBNotification {

    public DBNotification(DashSQLException e, String message) {
        String text = "SQL {Text=" + e.getSql().getMessage() + ",\n message=[" + e.getMessage() + "],\n " + message + "}";
        Notification n = new Notification("ERROR", text, Notification.Type.ERROR_MESSAGE);
        n.setDelayMsec(20000);
        n.setPosition(Position.MIDDLE_CENTER);
        n.show(Page.getCurrent());
    }
}
