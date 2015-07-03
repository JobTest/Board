package com.pb.dashboard.monitoring.errors.all.table;

import com.pb.dashboard.dao.entity.biplanesupport.db.SessionDebtData;
import com.vaadin.ui.Table;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vlad
 * Date: 23.09.14
 * Time: 10:47
 * To change this template use File | Settings | File Templates.
 */
public class SessionDebtTable extends Table {

    private static final String INTERFACE_NAME = "interface_name";
    private static final String MODULE = "module";
    private static final String ERROR_CODE = "error_code";
    private static final String STATUS = "status";
    private static final String LOGGER_NAME = "logger_name";
    private static final String START_TIME = "start_time";
    private static final String METHOD = "method";
    private static final String DOP_INFO = "dop_info";
    private static final String COMPANY_ID = "company_id";
    private static final String POINT_TYPE = "point_type";
    private static final String DEBT_TYPE = "debt_type";

    public SessionDebtTable() {
        init();
    }

    public SessionDebtTable(List<SessionDebtData> data) {
        init();
        setDebtData(data);
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
        addContainerProperty(LOGGER_NAME, String.class, null);
        addContainerProperty(START_TIME, String.class, null);
        addContainerProperty(METHOD, String.class, null);
        addContainerProperty(DOP_INFO, String.class, null);
        addContainerProperty(COMPANY_ID, String.class, null);
        addContainerProperty(POINT_TYPE, String.class, null);
        addContainerProperty(DEBT_TYPE, String.class, null);

        setColumnExpandRatio(INTERFACE_NAME, 1f);
        setColumnExpandRatio(MODULE, 1f);
        setColumnExpandRatio(ERROR_CODE, 1f);
        setColumnExpandRatio(STATUS, 1f);
        setColumnExpandRatio(LOGGER_NAME, 1f);
        setColumnExpandRatio(START_TIME, 1f);
        setColumnExpandRatio(METHOD, 1f);
        setColumnExpandRatio(DOP_INFO, 1f);
        setColumnExpandRatio(COMPANY_ID, 1f);
        setColumnExpandRatio(POINT_TYPE, 1f);
        setColumnExpandRatio(DEBT_TYPE, 1f);

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
                Table.Align.CENTER,
                Table.Align.CENTER
        );
    }

    public void setDebtData(List<SessionDebtData> debts) {
        removeAllItems();
        setPageLength(debts.size());
        for (SessionDebtData debt : debts) {
            addItem(new Object[]{
                    debt.getInterfaceName(),
                    debt.getModule(),
                    debt.getErrorCode(),
                    debt.getStatus(),
                    debt.getLoggerName(),
                    debt.getStartTime(),
                    debt.getMethod(),
                    debt.getDopInfo(),
                    debt.getCompanyId(),
                    debt.getPointType(),
                    debt.getDebtType()
            }, null);
        }
    }
}