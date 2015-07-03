package com.pb.dashboard.monitoring.errors.all.second;

import com.pb.dashboard.dao.entity.biplanepl.ErrorDetailI;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.monitoring.errors.all.strategy.PageType;
import com.pb.dashboard.monitoring.errors.all.table.listener.TreeTableListener;
import com.pb.dashboard.monitoring.errors.all.table.model.ErrorModel;
import com.pb.dashboard.monitoring.errors.all.table.model.RecipientModel;
import com.vaadin.ui.Button;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * User: vlad Date: 12.09.14
 */
public class SecondController implements SecondControllerI {

    private static final Logger log = Logger.getLogger(SecondController.class);
    private SecondModel model;
    private SecondView view;

    public SecondController() {
        init();
    }

    private void init() {
        initModel();
        initView();
    }

    private void initModel() {
        model = new SecondModel();
    }

    private void initView() {
        view = new SecondView(this, model);
        view.addCodeClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (model.getPageType() == PageType.RECIPIENT) {
                    boolean visible = model.isDescriptionVisible();
                    if (!visible) {
                        model.setErrorDetail(getErrorDetail());
                    }
                    model.setDescriptionVisible(!visible);
                    model.notifyModifiedAll();
                }
            }
        });
    }

    private ErrorDetailI getErrorDetail() {
        return ServiceFactory.getMonitoring().getErrorDetail(model.getCode());
    }

    @Override
    public void setList(List list, PageType type) {
        model.setPageType(type);
        if (validTypeList(list)) {
            model.setList(list);
        }
        model.notifyModifiedAll();
    }

    private boolean validTypeList(List list) {
        if (list.isEmpty()) {
            return true;
        }
        Object item = list.get(0);
        if (item instanceof ErrorModel || item instanceof RecipientModel) {
            return true;
        }
        log.error("Type[" + item.getClass() + "] not valid", new IllegalArgumentException());
        return false;
    }

    @Override
    public void setCode(String code) {
        model.setCode(code);
        model.notifyModifiedAll();
    }

    @Override
    public void setDescriptionVisible(boolean visible) {
        model.setDescriptionVisible(visible);
        model.notifyModifiedAll();
    }

    @Override
    public void addBackClickListener(Button.ClickListener listener) {
        view.addBackClickListener(listener);
    }

    @Override
    public void setTreeTableListener(TreeTableListener listener) {
        view.setListener(listener);
    }

    @Override
    public SecondView getView() {
        return view;
    }

    @Override
    public SecondModelI getModel() {
        return model;
    }
}