package com.pb.dashboard.monitoring.components.navigator;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.vaadin.hene.popupbutton.PopupButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NavigatorView extends VerticalLayout implements NavigatorListener {

    public static final String SEPARATOR_CHART = ">";

    private NavigatorModelI model;
    private NavigatorControllerI controller;

    private Map<Integer, PopupButton> popups;
    private HorizontalLayout currentLayout;

    public NavigatorView(NavigatorControllerI controller, NavigatorModelI model) {
        this.controller = controller;
        this.model = model;
        model.setListener(this);
        init();
    }

    private void init() {
        setStyleName("timings-navigation-header");
        nextLine();
        popups = new HashMap<Integer, PopupButton>();

        Map<Integer, NavigatorContent> contentMap = model.getContentMap();
        int i = 0;
        for (Map.Entry<Integer, NavigatorContent> item : contentMap.entrySet()) {
            for (int line : model.getPointNewLine()) {
                if (i == line) {
                    nextLine();
                }
            }
            Integer key = item.getKey();
            NavigatorContent value = item.getValue();
            setItems(key, value.getItems());
            setItem(key, value.getItem());
            setEnabled(key, value.isEnabled());
            i++;
        }
    }

    @Override
    public void setItems(int navigatorId, List<ContentItem> items) {
        PopupButton popupButton = popups.get(navigatorId);
        if (popupButton == null) {
            popupButton = addPopupButton(navigatorId);
        }
        if (items != null && !items.isEmpty()) {
            VerticalLayout panel = createButtonsPanel(navigatorId, items, popupButton);
            popupButton.setContent(panel);
            setItem(navigatorId, items.get(0));
        }
    }

    @Override
    public void setItem(int navigatorId, ContentItem item) {
        if (item == null) {
            return;
        }
        PopupButton popupButton = popups.get(navigatorId);
        if (popupButton == null) {
            popupButton = addPopupButton(navigatorId);
        }
        popupButton.setCaption(item.getName());
    }

    @Override
    public void setEnabled(int navigatorId, boolean enabled) {
        PopupButton popupButton = popups.get(navigatorId);
        if (popupButton == null) {
            popupButton = addPopupButton(navigatorId);
        }
        popupButton.setEnabled(enabled);
    }

    private PopupButton addPopupButton(int id) {
        PopupButton popupButton = new PopupButton();
        popupButton.addStyleName("charts-label");
        if (currentLayout.getComponentCount() != 0) {
            currentLayout.addComponent(createSeparator());
        }
        currentLayout.addComponent(popupButton);
        popups.put(id, popupButton);
        return popupButton;
    }

    private Label createSeparator() {
        Label sep = new Label(SEPARATOR_CHART);
        sep.addStyleName("charts-label");
        sep.setWidth("40px");
        return sep;
    }

    private VerticalLayout createButtonsPanel(int id, final List<ContentItem> items, final PopupButton popup) {
        VerticalLayout layout = new VerticalLayout();
        for (final ContentItem item : items) {
            Button.ClickListener clickListener = createClickListener(id, item, popup);
            Button button = createButton(item.getName(), clickListener);
            layout.addComponent(button);
        }
        return layout;
    }

    private Button.ClickListener createClickListener(final int id, final ContentItem item, final PopupButton popup) {
        return new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                popup.setPopupVisible(false);
                controller.setItem(id, item);
            }
        };
    }

    private Button createButton(String caption, Button.ClickListener listener) {
        Button button = new Button(caption);
        button.setStyleName(Reindeer.BUTTON_LINK);
        button.addClickListener(listener);
        return button;
    }

    public void nextLine() {
        currentLayout = new HorizontalLayout();
        currentLayout.setMargin(new MarginInfo(false, false, true, false));
        addComponent(currentLayout);
        setComponentAlignment(currentLayout, Alignment.BOTTOM_CENTER);
    }
}