package com.pb.dashboard.monitoring.errors.all.table;

import com.pb.dashboard.monitoring.errors.all.db.container.SessionTempData;
import com.vaadin.ui.Table;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vlad
 * Date: 23.09.14
 * Time: 10:47
 * To change this template use File | Settings | File Templates.
 */
public class SessionTempTable extends Table {

    private static final String INTERFACE_NAME = "interface_name";
    private static final String MODULE = "module";
    private static final String ERROR_CODE = "error_code";
    private static final String STATUS = "status";
    private static final String HTTP_STATUS = "http_status";
    private static final String LOGGER_NAME = "logger_name";
    private static final String START_TIME = "start_time";
    private static final String DURATION = "duration";
    private static final String METHOD = "method";
    private static final String DOP_INFO = "dop_info";
    private static final String CLIENT_ERROR_CODE = "client_error_code";

    public SessionTempTable() {
        init();
    }

    public SessionTempTable(List<SessionTempData> list) {
        init();
        setTempData(list);
    }

    private void init() {
        setImmediate(true);
        setPageLength(0);
        setWidth("100%");
        setStyleName("monitoring");
        addContainerProperty(INTERFACE_NAME, String.class, null);
        addContainerProperty(MODULE, String.class, null);
        addContainerProperty(ERROR_CODE, String.class, null);
        addContainerProperty(STATUS, String.class, null);
        addContainerProperty(HTTP_STATUS, String.class, null);
        addContainerProperty(LOGGER_NAME, String.class, null);
        addContainerProperty(START_TIME, String.class, null);
        addContainerProperty(DURATION, String.class, null);
        addContainerProperty(METHOD, String.class, null);
        addContainerProperty(DOP_INFO, String.class, null);
        addContainerProperty(CLIENT_ERROR_CODE, String.class, null);

        setColumnExpandRatio(INTERFACE_NAME, 1f);
        setColumnExpandRatio(MODULE, 1f);
        setColumnExpandRatio(ERROR_CODE, 1f);
        setColumnExpandRatio(STATUS, 1f);
        setColumnExpandRatio(HTTP_STATUS, 1f);
        setColumnExpandRatio(LOGGER_NAME, 1f);
        setColumnExpandRatio(START_TIME, 1f);
        setColumnExpandRatio(DURATION, 1f);
        setColumnExpandRatio(METHOD, 1f);
        setColumnExpandRatio(DOP_INFO, 1f);
        setColumnExpandRatio(CLIENT_ERROR_CODE, 1f);

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
                Align.CENTER,
                Align.CENTER
        );
    }

    public void setTempData(List<SessionTempData> temps) {
        removeAllItems();
        setPageLength(temps.size());
        for (SessionTempData temp : temps) {
            addItem(new Object[]{
                    temp.getInterfaceName(),
                    temp.getModule(),
                    temp.getErrorCode(),
                    temp.getStatus(),
                    temp.getHttpStatus(),
                    temp.getLoggerName(),
                    temp.getStartTime(),
                    temp.getDuration(),
                    temp.getMethod(),
                    temp.getDopInfo(),
                    temp.getClientErrorCode()
            }, null);
        }
    }
}