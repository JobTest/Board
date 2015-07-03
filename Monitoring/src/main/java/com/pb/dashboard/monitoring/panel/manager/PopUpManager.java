package com.pb.dashboard.monitoring.panel.manager;

import com.pb.dashboard.core.hierarchy.Dashboard;
import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.model.Country;
import com.pb.dashboard.core.util.UrlParamBuilder;
import com.pb.dashboard.dao.entity.biplanesupport.db.ErrorsTypeI;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.monitoring.components.filter.FilterModel;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.pb.dashboard.monitoring.components.parameter.MonitoringParam;
import com.pb.dashboard.monitoring.errors.all.navigator.NavigatorModelBuilder;
import com.pb.dashboard.monitoring.timings.filter.FilterModelBuilder;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Reindeer;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.pb.dashboard.monitoring.components.parameter.MonitoringParam.*;

public class PopUpManager implements Serializable {

    private static final long serialVersionUID = -5878934721266964799L;

    public VerticalLayout getInterfacesPopUp(@Nonnull Country country, @Nonnull Complex complex) {
        Map<Integer, DInterfaceI> interfaces = ServiceFactory.getMonitoring().getInterfaces(complex.getId(), country.getId());
        return getInterfacesPopUp(country, complex, new ArrayList<>(interfaces.values()));
    }

    private VerticalLayout getInterfacesPopUp(Country country, Complex complex, List<DInterfaceI> interfaces) {
        VerticalLayout layout = new VerticalLayout();
        for (DInterfaceI bpInterface : interfaces) {
            String mainPath = Dashboard.Biplane.Monitoring.Timings.PATH;
            String urlPath = buildUrlParam(country, complex, bpInterface).toString();
            String path = "#!" + mainPath + "/" + urlPath;
            Link link = new Link(bpInterface.getDescription(), new ExternalResource(path));
            layout.addComponent(link);
        }
        return layout;
    }

    public VerticalLayout getMetricsErrors(final Country country, final Complex complex) {
        VerticalLayout layout = new VerticalLayout();
        List<ErrorsTypeI> types = NavigatorModelBuilder.getTypesErrors(country, complex);
        for (final ErrorsTypeI type : types) {
            Button button = new Button(type.getName());
            button.setStyleName(Reindeer.BUTTON_LINK);
            button.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    crossingToErrorsAll(country, complex, type);
                }
            });
            layout.addComponent(button);
        }
        return layout;
    }

    private void crossingToErrorsAll(Country country, Complex complex, ErrorsTypeI object) {
        String mainPath = Dashboard.Biplane.Monitoring.Errors.All.PATH;
        String urlParams = buildParamsErrorsAll(country, complex, object);
        UI.getCurrent().getNavigator().navigateTo(mainPath + "/" + urlParams);
    }

    private String buildParamsErrorsAll(Country country, Complex complex, ErrorsTypeI metric) {
        UrlParamBuilder url = new UrlParamBuilder()
                .addParam(MonitoringParam.COUNTRY, country.getId())
                .addParam(MonitoringParam.COMPLEX, complex.getId())
                .addParam(MonitoringParam.TYPE, metric.getName());
        return url.toString();
    }

    public VerticalLayout getInterfacesPopUp(final Country country, final Complex complex, List<DInterfaceI> interfaces, final FilterModel filterModel) {
        VerticalLayout layout = new VerticalLayout();
        if (interfaces != null) {
            for (final DInterfaceI bpInterface : interfaces) {
                Button button = new Button(bpInterface.getDescription());
                button.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        String mainPath = Dashboard.Biplane.Monitoring.Timings.PATH;
                        UrlParamBuilder urlParam = buildUrlParam(country, complex, bpInterface);
                        UrlParamBuilder url = addFilterUrl(urlParam, filterModel);
                        UI.getCurrent().getNavigator().navigateTo(mainPath + "/" + url.toString());
                    }
                });
                button.setStyleName(BaseTheme.BUTTON_LINK);
                layout.addComponent(button);
            }
        }
        return layout;
    }

    private UrlParamBuilder addFilterUrl(UrlParamBuilder builder, FilterModel model) {
        String pattern = FilterModelBuilder.DATE_PATTERN;
        builder
                .addParam(RANGE, model.getFilterRange().getPkey())
                .addParam(REGLAMENT, model.isReglament());
        if (model.getFilterRange() == FilterRange.DAY) {
            builder.addParam(DATE_FROM, model.getDateFrom().toString(pattern));
            builder.addParam(DATE_TO, model.getDateTo().toString(pattern));
        } else {
            builder.addParam(DATE, model.getDate().toString(pattern));
        }
        return builder;
    }

    public VerticalLayout getMetricsPopUp(final Country country, final Complex complex,
                                          final DInterfaceI bpInterface, List<InterfaceMetricI> metrics) {
        VerticalLayout layout = new VerticalLayout();
        if (metrics != null) {
            for (InterfaceMetricI metric : metrics) {
                Button button = new Button(metric.getDescription());
                button.setStyleName(Reindeer.BUTTON_LINK);
                button.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        crossingByUrl(Dashboard.Biplane.Monitoring.Timings.PATH, buildUrlParam(country, complex, bpInterface));
                    }
                });
                layout.addComponent(button);
            }
        }
        return layout;
    }

    private void crossingByUrl(String mainPath, UrlParamBuilder builder) {
        String path = mainPath + "/" + builder.toString();
        UI.getCurrent().getNavigator().navigateTo(path);
    }

    private UrlParamBuilder buildUrlParam(Country country, Complex complex, DInterfaceI bpInterface) {
        return new UrlParamBuilder()
                .addParam(MonitoringParam.COUNTRY.getName(), country.getId())
                .addParam(MonitoringParam.COMPLEX.getName(), complex.getId())
                .addParam(MonitoringParam.INTERFACE.getName(), bpInterface.getPkey());
    }
}
