package com.pb.dashboard.monitoring.timings;

import com.github.wolfie.refresher.Refresher;
import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.model.Country;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceLimitI;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.monitoring.components.filter.*;
import com.pb.dashboard.monitoring.components.navigator.*;
import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.timings.datamanager.LoadData;
import com.pb.dashboard.monitoring.timings.datamanager.ReglamentFilter;
import com.pb.dashboard.monitoring.timings.datamanager.StrategyFactory;
import com.pb.dashboard.monitoring.timings.datamanager.StrategyI;
import com.pb.dashboard.monitoring.timings.filter.FilterModelBuilder;
import com.pb.dashboard.monitoring.timings.navigator.NavigatorItem;
import com.pb.dashboard.monitoring.timings.navigator.NavigatorModelAdapter;
import com.pb.dashboard.monitoring.timings.navigator.NavigatorModelBuilder;
import com.pb.dashboard.monitoring.timings.tabsheet.IndicatorsController;
import com.pb.dashboard.monitoring.timings.tabsheet.IndicatorsControllerI;
import com.pb.dashboard.monitoring.timings.tabsheet.IndicatorsModel;
import com.pb.dashboard.monitoring.timings.tabsheet.IndicatorsTabSheet;
import com.pb.dashboard.monitoring.timings.transferlink.LinkManager;
import com.pb.dashboard.monitoring.timings.transferlink.LinkManagerI;
import com.vaadin.ui.UI;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

import static com.pb.dashboard.monitoring.timings.navigator.NavigatorItem.*;

/**
 * Created by vlad
 * Date: 20.11.14_9:32
 */
public class TimingsController implements TimingsControllerI, Observer<FilterModelI>, NavigatorObserver {

    private TimingsView view;
    private TimingsModelI model;

    private FilterControllerI filterController;
    private NavigatorControllerI navigatorController;
    private IndicatorsControllerI indicatorsController;
    private LoadData loadData = new LoadData();
    private LinkManagerI linkManager;
    private StrategyI strategy;
    private Refresher refresher;

    public TimingsController(TimingsView view) {
        this.view = view;
        init();
    }

    private void init() {
        initModel();
        initControllers();
        initView();
        initRefresher();
        updateStrategy();
        updateDataIndicators();
        updateData();
    }

    private void initControllers() {
        filterController = new FilterController(model.getFilter());
        navigatorController = new NavigatorController(model.getNavigator());
        indicatorsController = new IndicatorsController(model.getIndicators());
        linkManager = new LinkManager(model.getFilter(), model.getNavigator());
        indicatorsController.setLinkManager(linkManager);
        addAllObservers();
    }

    private void addAllObservers() {
        FilterModelI filter = model.getFilter();
        NavigatorModelAdapter navigator = model.getNavigator();
        filter.addObserver(indicatorsController);
        filter.addObserver(this);
        navigator.addObserver(indicatorsController);
        navigator.addObserver(this);
        int pkey = NavigatorItem.INTERFACE.getPkey();
        ContentItem item = navigator.getItem(pkey);
        indicatorsController.setItem(pkey, item);
        indicatorsController.modified(filter);
    }

    private void initModel() {
        Map<String, String> map = view.getParameters();
        model = new TimingsModel();
        NavigatorModelAdapter navigatorModelAdapter = NavigatorModelBuilder.build(map);
        model.setNavigator(navigatorModelAdapter);
        FilterModel filterModel = FilterModelBuilder.build(map);
        model.setFilter(filterModel);
        model.setIndicators(new IndicatorsModel());
    }

    private void initView() {
        view.setModel(model);
    }

    public void initRefresher() {
        refresher = new Refresher();
        refresher.addListener(new Refresher.RefreshListener() {
            @Override
            public void refresh(Refresher refresher) {
                updateData();
            }
        });
        view.addRefresher(refresher);
    }

    @Override
    public void setItem(int navigatorId, ContentItem item) {
        NavigatorModelAdapter navigator = model.getNavigator();
        if (navigatorId == COMPLEX.getPkey()) {
            Country country = navigator.getCountry();
            Complex complex = navigator.getComplex();
            Map<Integer, DInterfaceI> interfaces = ServiceFactory.getMonitoring().getInterfaces(complex.getId(), country.getId());
            navigator.setInterfaces(interfaces);
        }
        if (navigatorId == INTERFACE.getPkey()) {
            Map<Integer, InterfaceMetricI> metrics = loadData.getInterfaceMetrics(item.getPkey());
            updateLimit();
            navigator.setMetrics(metrics);
        }
        if (navigatorId == METRIC.getPkey()) {
            updateSelectedMetric();
            updateUrlParams();
            updateData();
        }
    }

    @Override
    public void modified(FilterModelI obj) {
        updateUrlParams();
        updateData();
    }

    private void updateUrlParams() {
        String path = linkManager.pathToTimings();
        UI.getCurrent().getNavigator().navigateTo(path);
    }

    private void updateData() {
        updateStrategy();

        List<InterfaceMetricI> metrics = model.getNavigator().getMetrics();
        FilterModelI filter = model.getFilter();
        LocalDate date = filter.getDate();
        LocalDate from = filter.getDateFrom();
        LocalDate to = filter.getDateTo();
        FilterRange range = filter.getFilterRange();
        boolean reglament = filter.isReglament();

        Map<InterfaceMetricI, List<MetricItem>> timingMetrics = strategy.getTimingsMetrics(metrics, date, from, to);
        Map<InterfaceMetricI, List<MetricItem>> filterItems = ReglamentFilter.filter(timingMetrics, range, reglament);
        indicatorsController.setData(filterItems, range);
    }

    private void updateStrategy() {
        FilterRange range = model.getFilter().getFilterRange();
        strategy = StrategyFactory.build(range, loadData);

        setReloadInterval();
    }

    private void setReloadInterval() {
        int mSec = strategy.getReloadTimeMSec();
        refresher.setRefreshInterval(mSec);
    }

    private void updateDataIndicators() {
        updateSelectedMetric();
        updateLimit();
    }

    private void updateSelectedMetric() {
        int pkey = model.getNavigator().getMetric().getPkey();
        model.getIndicators().setMetricSelected(pkey);
    }

    private void updateLimit() {
        int pkey = model.getNavigator().getBpInterface().getPkey();
        InterfaceLimitI limit = loadData.getInterfaceLimit(pkey);
        indicatorsController.setLimit(limit);
    }

    @Override
    public NavigatorView getNavigatorView() {
        return navigatorController.getView();
    }

    @Override
    public FilterView getFilterView() {
        return filterController.getView();
    }

    @Override
    public IndicatorsTabSheet getIndicators() {
        return indicatorsController.getView();
    }

    @Override
    public void update() {
        Map<String, String> map = view.getParameters();
        NavigatorModelAdapter data = NavigatorModelBuilder.build(map);
        NavigatorModelAdapter navigator = model.getNavigator();
        if (!navigator.getCountry().equals(data.getCountry())) {
            navigator.setCountry(data.getCountry());
        }
        if (!navigator.getComplex().equals(data.getComplex())) {
            navigator.setComplex(data.getComplex());
        }
        if (!navigator.getBpInterface().equals(data.getBpInterface())) {
            navigator.setBpInterface(data.getBpInterface());
        }
    }
}