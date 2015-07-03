package com.pb.dashboard.monitoring.errors.all.table;

import com.pb.dashboard.dao.entity.biplanesupport.db.SessionErrorsData;
import com.vaadin.ui.Table;

import java.util.List;

public class SessionErrorsTable extends Table {

    private static final String LOGGER_NAME = "l_name";
    private static final String INTERFACE_NAME = "i_name";
    private static final String START_TIME = "start_time";
    private static final String MODULE = "module";
    private static final String ERROR_TEXT = "error_text";
    private static final String CLIENT_ERROR_CODE = "client_error_code";
    private static final String TEXT_MESSAGE = "text_message";
    private static final String SERVICE_ID = "service_id";
    private static final String ALIAS = "alias";
    private static final String CLIENT_IP_ADDRESS = "client_ip_address";

    public SessionErrorsTable() {
        init();
    }

    private void init() {
        initStyle();
        setProperties();
        setAlignments();
        setColumnRatios();
    }

    private void initStyle() {
        addStyleName("monitoring");
        setImmediate(true);
        setSizeFull();
        setPageLength(0);
        setFooterVisible(true);
        setSelectable(true);
    }

    private void setProperties() {
        addContainerProperty(LOGGER_NAME, String.class, null);
        addContainerProperty(INTERFACE_NAME, String.class, null);
        addContainerProperty(START_TIME, String.class, null);
        addContainerProperty(MODULE, String.class, null);
        addContainerProperty(ERROR_TEXT, String.class, null);
        addContainerProperty(CLIENT_ERROR_CODE, String.class, null);
        addContainerProperty(TEXT_MESSAGE, String.class, null);
        addContainerProperty(SERVICE_ID, String.class, null);
        addContainerProperty(ALIAS, String.class, null);
        addContainerProperty(CLIENT_IP_ADDRESS, String.class, null);
    }

    private void setAlignments() {
        setColumnAlignments(
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER);
    }

    private void setColumnRatios() {
        setColumnExpandRatio(LOGGER_NAME, 0.8f);
        setColumnExpandRatio(INTERFACE_NAME, 0.8f);
        setColumnExpandRatio(START_TIME, 1.3f);
        setColumnExpandRatio(MODULE, 2f);
        setColumnExpandRatio(ERROR_TEXT, 6f);
        setColumnExpandRatio(CLIENT_ERROR_CODE, 1.7f);
        setColumnExpandRatio(TEXT_MESSAGE, 5f);
        setColumnExpandRatio(SERVICE_ID, 2f);
        setColumnExpandRatio(ALIAS, 1f);
        setColumnExpandRatio(CLIENT_IP_ADDRESS, 2f);
    }

    public void setErrors(List<SessionErrorsData> errors) {
        removeAllItems();
        for (SessionErrorsData error : errors) {
            addItem(new Object[]{
                    error.getLoggerName(),
                    error.getInterfaceName(),
                    error.getStartTime(),
                    error.getModule(),
                    error.getErrorText(),
                    error.getClientErrorCode(),
                    error.getTextMessage(),
                    error.getServiceId(),
                    error.getAlias(),
                    error.getClientIpAddress()
            }, null);
        }
        setPageLength(errors.size());
    }
}