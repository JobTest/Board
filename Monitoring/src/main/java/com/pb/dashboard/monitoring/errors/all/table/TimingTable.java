package com.pb.dashboard.monitoring.errors.all.table;

import com.pb.dashboard.dao.entity.biplanesupport.db.TimingData;
import com.vaadin.ui.Table;

import java.util.List;

public class TimingTable extends Table {

    private static final String LOGGER_NAME = "l_name";
    private static final String INTERFACE_NAME = "in_name";
    private static final String SESSION_ID = "session_id";
    private static final String START_TIME = "start_time";
    private static final String DURATION = "duration";
    private static final String STATUS = "status";
    private static final String DOP_INFO = "dop_info";
    private static final String ERROR_CODE = "error_code";
    private static final String MODULE = "module";
    private static final String METHOD = "method";

    public TimingTable() {
        setImmediate(true);
        setPageLength(0);
        setWidth("100%");
        setStyleName("monitoring");
        addStyleName("small");
        addContainerProperty(LOGGER_NAME, String.class, null);
        addContainerProperty(INTERFACE_NAME, String.class, null);
        addContainerProperty(SESSION_ID, String.class, null);
        addContainerProperty(START_TIME, String.class, null);
        addContainerProperty(DURATION, String.class, null);
        addContainerProperty(STATUS, String.class, null);
        addContainerProperty(DOP_INFO, String.class, null);
        addContainerProperty(ERROR_CODE, String.class, null);
        addContainerProperty(MODULE, String.class, null);
        addContainerProperty(METHOD, String.class, null);

        setColumnExpandRatio(LOGGER_NAME, 0.8f);
        setColumnExpandRatio(INTERFACE_NAME, 1.2f);
        setColumnExpandRatio(SESSION_ID, 1f);
        setColumnExpandRatio(START_TIME, 1.5f);
        setColumnExpandRatio(DURATION, 0.6f);
        setColumnExpandRatio(STATUS, 0.6f);
        setColumnExpandRatio(DOP_INFO, 2f);
        setColumnExpandRatio(ERROR_CODE, 1f);
        setColumnExpandRatio(MODULE, 2f);
        setColumnExpandRatio(METHOD, 4f);

        setColumnAlignments(
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER
        );
    }

    public TimingTable(List<TimingData> list) {
        this();
        setTimings(list);
    }

    public void setTimings(List<TimingData> timings) {
        removeAllItems();
        for (TimingData timing : timings) {
            addItem(new Object[]{
                    timing.getLoggerName(),
                    timing.getInterfaceName(),
                    timing.getSessionId(),
                    timing.getStartTime(),
                    timing.getDuration(),
                    timing.getStatus(),
                    timing.getDopInfo(),
                    timing.getErrorCode(),
                    timing.getModule(),
                    timing.getMethod()
            }, null);
        }
        if (!timings.isEmpty()) {
            if (timings.get(0).getSessionId() == null) {
                setVisibleColumns(LOGGER_NAME, INTERFACE_NAME, START_TIME, DURATION, STATUS, DOP_INFO, ERROR_CODE, MODULE, METHOD);
            } else {
                setVisibleColumns(LOGGER_NAME, INTERFACE_NAME, SESSION_ID, START_TIME, DURATION, STATUS, DOP_INFO, ERROR_CODE, MODULE, METHOD);
            }
        }
        setPageLength(timings.size());
    }
}
