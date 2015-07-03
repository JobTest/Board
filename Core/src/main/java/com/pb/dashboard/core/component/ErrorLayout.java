package com.pb.dashboard.core.component;

import com.pb.dashboard.core.util.LogUtil;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.vaadin.hene.popupbutton.PopupButton;

public class ErrorLayout extends VerticalLayout {

    private static final String EXEC_ERROR = "Извините, произошла ошибка!";
    private String redirectUrl;
    private Label errorLabel;
    private PopupButton button;

    public ErrorLayout(Exception e, String redirectUrl) {
        this.redirectUrl = redirectUrl;
        initErrorLabel();
        initErrorPopup(e);
        addComponent(buildContent());
    }

    private void initErrorLabel() {
        errorLabel = new Label();
        errorLabel.setSizeUndefined();
        errorLabel.addStyleName("metricType");
        errorLabel.setValue(EXEC_ERROR);
    }

    private void initErrorPopup(Exception e) {
        button = new PopupButton(e.toString());

        String error = LogUtil.stackTraceToString(e);
        TextArea area = new TextArea();
        area.setWidth("800px");
        area.setHeight("600px");
        area.setStyleName("error");
        area.setValue(error);
        button.setContent(area);
    }

    private Component buildContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();

        VerticalLayout inner = new VerticalLayout();
        inner.setSpacing(true);
        inner.setSizeUndefined();
        Image image = new Image(null, new ThemeResource("img/timings/broken_link.png"));
        String context = Page.getCurrent().getLocation().getPath();
        Link link = new Link("Вернуться на главную страницу", new ExternalResource(context + "#!" + redirectUrl));
        inner.addComponents(image, errorLabel, button, link);
        inner.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
        inner.setComponentAlignment(errorLabel, Alignment.MIDDLE_CENTER);
        inner.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        inner.setComponentAlignment(link, Alignment.MIDDLE_CENTER);

        layout.addComponents(new Label(), inner, new Label());
        layout.setComponentAlignment(inner, Alignment.TOP_CENTER);
        return layout;
    }
}
