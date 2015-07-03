package com.pb.dashboard.monitoring.errors.outer;

import com.pb.dashboard.core.error.DashSQLException;
import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.monitoring.errors.all.db.mongo.ErrorsDBManagerAbs;
import com.pb.dashboard.monitoring.errors.all.window.SessionController;
import com.pb.dashboard.monitoring.errors.outer.table.OuterSessionData;
import com.pb.dashboard.monitoring.errors.outer.table.listener.ClickSessionListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by evasive on 04.11.14
 */

public class OuterController {

    private static final Logger LOG = Logger.getLogger(OuterController.class);
    private static final String OUTER_SESSION_PATTERN = "^[A-Za-z0-9._-]{1,55}$";

    private static final String NO_DATA_FOR_THE_PERIOD = "Нет данных за указанный период.";
    private static final String INCORRECT_SYMBOL = "Недопустимый символ!";
    private static final String FIELD_IS_EMPTY = "Поле outer_session_id не заполнено!";

    private OuterView view;
    private OuterModel model;

    public OuterController() {
        init();
    }

    private void init() {
        initModel();
        initView();
    }

    private void initModel() {
        model = new OuterModel();
    }

    private void initView() {
        view = new OuterView();
        view.setClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                String outerSessionId = view.getSearchValue().trim();
                if (isOuterSessionIdNotEmptyAndValid(outerSessionId)) {
                    fillSessionTable(outerSessionId);
                }
            }
        });
        view.setClickSessionListener(getListener());
    }

    public List<OuterSessionData> getSessionList(String outerSessionId, Date date) {
        List<OuterSessionData> outerSessionList = new ArrayList<OuterSessionData>();
        try {
            outerSessionList = ErrorsDBManagerAbs.getInstance().getDataByOuterSession(outerSessionId, date);
        } catch (DashSQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return outerSessionList;
    }

    private boolean isOuterSessionIdNotEmptyAndValid(String outerSessionId) {
        if (outerSessionId == null || outerSessionId.isEmpty()) {
            Notification.show(FIELD_IS_EMPTY);
            return false;
        }
        if (!isOuterSessionIdValid(outerSessionId)) {
            Notification.show(INCORRECT_SYMBOL);
            return false;
        }
        return true;
    }

    private boolean isOuterSessionIdValid(String outerSessionId) {
        Pattern pattern = Pattern.compile(OUTER_SESSION_PATTERN);
        Matcher matcher = pattern.matcher(outerSessionId);
        return matcher.matches();
    }

    private void fillSessionTable(String outerSessionId) {
        Date dateValue = view.getDateValue();
        List<OuterSessionData> sessions = getSessionList(outerSessionId, dateValue);
        if (!sessions.isEmpty()) {
            view.setSessions(sessions);
        } else {
            view.setTableVisible(false);
            Notification.show(NO_DATA_FOR_THE_PERIOD);
        }
    }

    private ClickSessionListener getListener() {
        return new ClickSessionListener() {
            @Override
            public void clickSession(String session) {
                new SessionController(Complex.BIPLANE_API2X, session);
            }
        };
    }

    public OuterView getView() {
        return view;
    }
}