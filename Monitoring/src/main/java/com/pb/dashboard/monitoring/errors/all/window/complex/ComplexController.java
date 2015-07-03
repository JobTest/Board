package com.pb.dashboard.monitoring.errors.all.window.complex;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.dao.entity.biplanesupport.db.SessionDebtData;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.monitoring.errors.all.db.container.SessionTempData;
import com.pb.dashboard.monitoring.errors.all.table.SessionDebtTable;
import com.pb.dashboard.monitoring.errors.all.table.SessionSessionsTable;
import com.pb.dashboard.monitoring.errors.all.table.SessionTempTable;
import com.pb.dashboard.monitoring.errors.all.table.listener.ClickListener;
import com.pb.dashboard.monitoring.errors.all.window.complex.tabs.TabsController;
import com.pb.dashboard.monitoring.errors.all.window.complex.tabs.TabsControllerI;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 22.09.14
 */
public class ComplexController implements ComplexControllerI {

    private static final Logger log = Logger.getLogger(ComplexController.class);

    private ComplexModel model;
    private ComplexView view;

    private TabsControllerI controller;
    private ClickListener listener;


    public ComplexController(Complex from, Complex complex, String sessionId) {
        model = new ComplexModel();
        model.setComplexFrom(from);
        model.setComplex(complex);
        model.setSessionId(sessionId);

        initController();
        initView();
        initListener();
    }

    private void initController() {
        controller = new TabsController(model.getComplex());
        if (isCurrentComplex()) {
            controller.setSessionId(model.getSessionId());
        }
    }

    private void initView() {
        view = new ComplexView();
        view.addTabSheet(controller.getView());
    }

    private void initListener() {
        listener = new ClickListener() {
            @Override
            public void clickCode(String code) {
                controller.setSessionId(code);
            }
        };
    }

    @Override
    public void update() {
        if (model.isLoad()) return;
        model.setLoad(true);
        initTable();
    }

    @Override
    public ComplexView getView() {
        return view;
    }


    private void initTable() {
        if (isCurrentComplex()) {
            return;
        }
        SessionSessionsTable sessionTable = new SessionSessionsTable();
        sessionTable.setListener(listener);
        view.setTable(sessionTable);
        switch (model.getComplex()) {
            case TEMPLATES:
                Map<String, List<SessionTempData>> temps = updateSessionData(SessionTempData.class);
                for (Map.Entry<String, List<SessionTempData>> item : temps.entrySet()) {
                    sessionTable.addSession(item.getKey(), new SessionTempTable(item.getValue()));
                }
                break;
            case BIPLANE_API2X:
            case DEBT:
                Map<String, List<SessionDebtData>> debts = updateSessionData(SessionDebtData.class);
                for (Map.Entry<String, List<SessionDebtData>> item : debts.entrySet()) {
                    sessionTable.addSession(item.getKey(), new SessionDebtTable(item.getValue()));
                }
                break;
        }
    }

    private <T> Map<String, List<T>> updateSessionData(Class<T> type) {
        List<T> list = ServiceFactory.getMonitoring().getSessionsByOuterSession(model.getComplexFrom(), model.getComplex(),
                model.getSessionId(), type);
        model.addListInfo(list);
        return model.getMapInfo();
    }

    private boolean isCurrentComplex() {
        return model.getComplex() == model.getComplexFrom();
    }
}