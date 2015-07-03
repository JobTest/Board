package com.pb.dashboard.monitoring.errors.panel;

import com.pb.dashboard.core.hierarchy.Dashboard;
import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.monitoring.components.parameter.MonitoringParam;
import com.pb.dashboard.monitoring.errors.all.panel.ErrorsManager;
import com.pb.dashboard.monitoring.errors.all.strategy.PanelView;
import com.pb.dashboard.monitoring.errors.outer.OuterController;
import com.pb.dashboard.monitoring.errors.outer.OuterView;
import com.vaadin.ui.Component;

import java.util.Map;

/**
 * Created by vlad
 * Date: 19.12.14_12:38
 */
public class ErrorsController {

    private ErrorsModel model;
    private ErrorsView view;

    private OuterController outerController;
    private ErrorsManager monitoringController;
    private static final String URL_SEPARATOR = "/";
    private static final int PARAM_LIMIT = 2;
    private static final int CONTEXT = 0;

    public ErrorsController(ErrorsView view, Map<String, String> params) {
        this.view = view;
        model = new ErrorsModel();
        model.setParams(params);
        init();
    }

    private void init() {
        initComplex();
    }

    private void initComplex() {
        String complexId = model.getParams().get(MonitoringParam.COMPLEX.getName());
        Complex complex;
        try {
            complex = Complex.pkeyToComplex(complexId);
        } catch (Exception e) {
            complex = Complex.BIPLANE_API2X;
        }
        model.setOuter(complex == Complex.BIPLANE_API2X);
    }

    public void setParameters(String param) {
        String context = param.split(URL_SEPARATOR, PARAM_LIMIT)[CONTEXT];
        String outer = Dashboard.Biplane.Monitoring.Errors.Outer.NAME;
        if (outer.equals(context)) {
            model.setComponent(getOuterController());
        } else {
            model.setComponent(getMonitoringView());
        }
        initComplex();
    }

    public PanelView getMonitoringView() {
        monitoringController = new ErrorsManager(model.getParams());
        return monitoringController.getView();
    }

    public OuterView getOuterController() {
        if (outerController == null) {
            outerController = new OuterController();
        }
        return outerController.getView();
    }

    public boolean haveOuter() {
        return model.isOuter();
    }

    public String getOuterPath() {
        return Dashboard.Biplane.Monitoring.Errors.Outer.PATH;
    }

    public String getAllPath() {
        return Dashboard.Biplane.Monitoring.Errors.All.PATH;
    }

    public Component getView() {
        return model.getComponent();
    }
}