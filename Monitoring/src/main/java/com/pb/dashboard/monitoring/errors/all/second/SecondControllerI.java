package com.pb.dashboard.monitoring.errors.all.second;

import com.pb.dashboard.monitoring.errors.all.strategy.PageType;
import com.pb.dashboard.monitoring.errors.all.table.listener.TreeTableListener;
import com.vaadin.ui.Button;

import java.util.List;

/**
 * Created by vlad
 * Date: 12.09.14
 */
public interface SecondControllerI {

    public void setList(List list, PageType type);

    public void setCode(String code);

    public void setDescriptionVisible(boolean open);

    public void addBackClickListener(Button.ClickListener listener);

    public void setTreeTableListener(TreeTableListener listener);

    public SecondView getView();

    public SecondModelI getModel();
}
