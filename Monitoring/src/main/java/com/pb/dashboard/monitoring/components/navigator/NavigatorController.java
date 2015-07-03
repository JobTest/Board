package com.pb.dashboard.monitoring.components.navigator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vlad
 * Date: 21.11.14_11:54
 */
public class NavigatorController implements NavigatorControllerI {

    private NavigatorView view;
    private NavigatorModelI model;

    public NavigatorController(NavigatorModelI model) {
        view = new NavigatorView(this, model);
        this.model = model;
    }

    @Override
    public void setPointsNewLine(int... lineNew) {
        Arrays.sort(lineNew);
        model.setPointsNewLine(lineNew);
    }

    @Override
    public void setItem(int id, ContentItem item) {
        model.setContent(id, item);
    }

    @Override
    public void setItems(int id, List<ContentItem> contents) {
        model.setContents(id, contents);
    }

    @Override
    public void setEnabled(int id, boolean enabled) {
        model.setEnabled(id, enabled);
    }

    public NavigatorView getView() {
        return view;
    }

    @Override
    public NavigatorModelI getModel() {
        return model;
    }
}
