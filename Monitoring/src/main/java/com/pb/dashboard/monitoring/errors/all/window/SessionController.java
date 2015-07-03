package com.pb.dashboard.monitoring.errors.all.window;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.monitoring.errors.all.window.complex.ComplexController;
import com.pb.dashboard.monitoring.errors.all.window.complex.ComplexControllerI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

public class SessionController {

    private static final Logger log = Logger.getLogger(SessionController.class);

    private Complex[] complexes = new Complex[]{Complex.BIPLANE_API2X, Complex.DEBT, Complex.TEMPLATES};
    private SessionWindow window;
    private SessionModel model;
    private TabSheet tabSheet;

    private Map<Complex, ComplexControllerI> controllers;

    public SessionController(Complex complex, String session) {
        controllers = new LinkedHashMap<Complex, ComplexControllerI>();

        model = new SessionModel();
        model.setComplex(complex);
        model.setSession(session);
        initTabs();
        initWindow();
        window.setContent(tabSheet);
        window.setVisible(true);
        UI.getCurrent().addWindow(window);
    }

    private void initWindow() {
        window = new SessionWindow();
    }

    public void addCloseListener(Window.CloseListener listener) {
        window.addCloseListener(listener);
    }

    private void initTabs() {
        tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        addTabComplex(model.getComplex());
        for (Complex complex : complexes) {
            if (model.getComplex() != complex) {
                addTabComplex(complex);
            }
        }

        tabSheet.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {
            @Override
            public void selectedTabChange(TabSheet.SelectedTabChangeEvent event) {
                TabSheet tabs = event.getTabSheet();
                TabSheet.Tab selectTab = tabs.getTab(tabs.getSelectedTab());
                String caption = selectTab.getCaption();
                Complex complex = Complex.BIPLANE_API2X;
                try {
                    complex = Complex.nameToComplex(caption);
                } catch (Exception e) {
                    log.warn("Complex name[" + caption + "] is not exists." + e.getClass(), e);
                }
                controllers.get(complex).update();
            }
        });
    }

    private ComplexControllerI addTabComplex(Complex complex) {
        ComplexControllerI controller = new ComplexController(model.getComplex(), complex, model.getSession());
        tabSheet.addTab(controller.getView(), complex.getName());
        controllers.put(complex, controller);
        return controller;
    }
}