package com.pb.dashboard.itdashbord.errors.errorSession;

import com.pb.dashboard.itdashbord.errors.error.title.TitleView;
import com.pb.dashboard.itdashbord.errors.errorSession.dater.ErrorsSessionDaterView;
import com.pb.dashboard.itdashbord.errors.errorSession.errorDownloader.ErrorDownloaderView;
import com.pb.dashboard.itdashbord.errors.errorSession.filter.FilterView;
import com.pb.dashboard.itdashbord.errors.errorSession.searchError.ErrorSearchView;
import com.pb.dashboard.itdashbord.errors.errorSession.tableComponent.ErrorsContentControler;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.Date;

public class ErrorsSessionByIdView extends VerticalLayout implements View {
    private Date dateFrom;
    private Date dateTo;
    private TitleView view;
    private ErrorsSessionDaterView errorsSessionDaterView;
    private ErrorsContentControler errorsContentControler;
    private FilterView filterComponent;
    private int companyId;
    private ErrorsSessionFiltersListener filter = new ErrorsSessionFiltersListener();
    private ErrorDownloaderView errorDownloaderView;
    private ErrorSearchView errorSearchView;

    public ErrorsSessionByIdView() {
        filterComponent = new FilterView(filter);
        errorSearchView= new ErrorSearchView(filter);
        init();
    }

    private void init() {
        setDateAndCompanyId();
        view = new TitleView();
        view.setWidth("100%");
        view.setHeight("150px");
        errorsSessionDaterView = new ErrorsSessionDaterView(dateFrom, dateTo, filter);
        errorsContentControler = new ErrorsContentControler(dateFrom.toString(), dateTo.toString(), companyId, filter);
        errorDownloaderView = new ErrorDownloaderView(errorsContentControler);

        addListeners();

        view.addComponent(errorSearchView, 0 , 0);
        view.setComponentAlignment(errorSearchView, Alignment.MIDDLE_CENTER);

        view.addComponent(errorsSessionDaterView, 1, 0);
        view.setComponentAlignment(errorsSessionDaterView, Alignment.MIDDLE_CENTER);

        view.addComponent(filterComponent,1,1);
        view.setComponentAlignment(filterComponent,Alignment.MIDDLE_CENTER);

        view.addComponent(errorDownloaderView, 0,1);
        view.setComponentAlignment(errorDownloaderView,Alignment.MIDDLE_CENTER);

        Label number = new Label("Ошибки     " + companyId);
        number.setHeight("30px");
        addComponent(number);
        addComponent(view);
        setExpandRatio(view, 1f);

        addComponent(errorsContentControler.getTable());
    }

    private void addListeners() {
        filterComponent.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                repaint();
            }
        });
        errorSearchView.getSearchButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                repaint();
            }
        });
    }

    private void repaint() {
        removeAllComponents();
        init();

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

    public void setDateAndCompanyId() {
        String[] s = Page.getCurrent().getUriFragment().split("/");
        companyId = Integer.valueOf(s[2]);

        dateFrom = new Date(Integer.valueOf(s[3])-1900,Integer.valueOf(s[4])-1,Integer.valueOf(s[5]));//nemnojko magii http://docs.oracle.com/javase/7/docs/api/java/sql/Date.html
        dateTo = new Date(Integer.valueOf(s[6])-1900,Integer.valueOf(s[7])-1,Integer.valueOf(s[8]));//i ewe 4ut 4ut'
    }
}
