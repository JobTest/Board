package com.pb.dashboard.monitoring.errors.all.table;

import com.pb.dashboard.core.util.DateUtil;
import com.pb.dashboard.monitoring.errors.all.db.container.TimingT2Data;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

import java.util.List;

public class TimingT2Table extends Table {

    private static final int DELAY_SEC = 15;
    private static final int DELAY_MILLIS = DELAY_SEC * DateUtil.MILLIS_IN_SEC;
    private static final String START_TIME = "start_time";
    private static final String DURATION = "duration";
    private static final String STATUS = "status";
    private static final String DOP_INFO = "dop_info";
    private static final String MODULE = "module";
    private static final String METHOD = "method";
    private List<TimingT2Data> timingsT2;

    public TimingT2Table() {
        setImmediate(true);
        setPageLength(0);
        setWidth("100%");
        setStyleName("monitoring");
        addStyleName("small");

        addContainerProperty(START_TIME, String.class, null);
        addContainerProperty(DURATION, String.class, null);
        addContainerProperty(STATUS, String.class, null);
        addContainerProperty(DOP_INFO, String.class, null);
        addContainerProperty(MODULE, String.class, null);
        addContainerProperty(METHOD, String.class, null);


        setColumnExpandRatio(START_TIME, 1f);
        setColumnExpandRatio(DURATION, 0.6f);
        setColumnExpandRatio(STATUS, 0.5f);
        setColumnExpandRatio(DOP_INFO, 2f);
        setColumnExpandRatio(MODULE, 1f);
        setColumnExpandRatio(METHOD, 1.2f);

        setColumnAlignments(
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER
        );

        addClickListener();
    }

    public TimingT2Table(List<TimingT2Data> timingT2) {
        this();
        setTimingsT2(timingT2);
    }

    private void addClickListener() {
        addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.getButton() == MouseEventDetails.MouseButton.RIGHT && event.getItemId() != null) {
                    String message = searchMessage(event.getItemId().toString());
                    showNotification(message);
                }
            }
        });
    }

    private String searchMessage(String item) {
        return timingsT2.get(Integer.parseInt(item)).getMessage();
    }

    private void showNotification(String text) {
        if (!text.isEmpty()) {
            Notification n = new Notification(text, Notification.Type.TRAY_NOTIFICATION);
            n.setStyleName("dark");
            n.setPosition(Position.MIDDLE_CENTER);
            n.setDelayMsec(DELAY_MILLIS);
            n.show(UI.getCurrent().getPage());
        }
    }

    public void setTimingsT2(List<TimingT2Data> timingsT2) {
        removeAllItems();
        int i = 0;
        for (TimingT2Data timing : timingsT2) {
            addItem(new Object[]{
                    timing.getAppTimestamp(),
                    timing.getDuration(),
                    timing.getStatus(),
                    timing.getDopInfo(),
                    timing.getModule(),
                    timing.getMethod()
            }, i++);
        }
        this.timingsT2 = timingsT2;
        setPageLength(timingsT2.size());
    }
}