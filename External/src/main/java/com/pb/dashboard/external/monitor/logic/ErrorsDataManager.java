package com.pb.dashboard.external.monitor.logic;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.dao.entity.biplanesupport.ErrorCountI;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.dao.service.ExternalServiceI;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.external.monitor.chart.ChartData;
import com.pb.dashboard.external.monitor.chart.ChartDataWrapper;
import com.pb.dashboard.external.monitor.entype.FilterRange;
import com.pb.dashboard.external.monitor.view.ErrorsViewPanelManager;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.Series;
import com.vaadin.addon.charts.model.style.SolidColor;
import org.joda.time.LocalDate;

import java.util.*;

import static com.pb.dashboard.external.monitor.entype.FilterRange.*;

public class ErrorsDataManager {

    private final static String SDF_PATTERN_YEAR_MONTH_DAY = "yyyy-MM-dd";
    private final static String SDF_PATTERN_YEAR_MONTH = "yyyy-MM";
    private final static String SDF_PATTERN_HR_MIN = "HH:mm";

    private String sdfForPrint;
    private ChartDataWrapper chartDataWrapper = new ChartDataWrapper();
    private ErrCntByInterfaceWrapper ecbiw = new ErrCntByInterfaceWrapper();
    private ChartData[] chartData = new ChartData[4];

    private static final int ALL_INTERFACE = -1;

    public ErrorsDataManager() {
        loadErrorsViewData();
    }

    public void loadErrorsViewData() {
        FilterRange range = R10MIN;
        DInterfaceI[] bpInterfaces = new DInterfaceI[]{ErrorsViewPanelManager.ALL_INTERFACES, ErrorsViewPanelManager.ALL_INTERFACES};
        Date[] dates = new Date[]{new Date()};
        loadPaymentsErrorsWhithChartDataWraper(range, bpInterfaces[0], dates);
        loadTemplErrorsWhithChartDataWraper(range, bpInterfaces[1], dates);
    }

    public void loadErrorsViewData(FilterRange range, DInterfaceI[] bpInterfaces, Date[] dates) {
        loadPaymentsErrorsWhithChartDataWraper(range, bpInterfaces[0], dates);
        loadTemplErrorsWhithChartDataWraper(range, bpInterfaces[1], dates);
    }

    public void loadPaymentsErrorsWhithChartDataWraper(FilterRange range, DInterfaceI bpInterface, Date[] dates) {
        String key = "pay";
        if (chartDataWrapper.contains(range, bpInterface, dates, key)) {
            chartData[0] = chartDataWrapper.getPaymentsBusinessErrors(range, bpInterface, dates, key);
            chartData[1] = chartDataWrapper.getPaymentsSystemErrors(range, bpInterface, dates, key);
        } else {
            loadPaymentsErrors(range, bpInterface, dates);
            chartDataWrapper.addChartData(range, new ChartData[]{chartData[0], chartData[1]}, bpInterface, dates, key);
        }
    }

    public void loadTemplErrorsWhithChartDataWraper(FilterRange range, DInterfaceI bpInterface, Date[] dates) {
        String key = "tem";
        if (chartDataWrapper.contains(range, bpInterface, dates, key)) return;
        loadTemplErrors(range, bpInterface, dates);
        chartDataWrapper.addChartData(range, new ChartData[]{chartData[2], chartData[3]}, bpInterface, dates, key);
    }


    private List<ErrorCountI> loadErrors(Complex complex, FilterRange range, DInterfaceI bpInterface, Date[] dates) {
        List<ErrorCountI> errors = new ArrayList<>();
        String bpInterfaceName = bpInterface.getPkey() == ALL_INTERFACE ? null : bpInterface.getName();

        ExternalServiceI dao = ServiceFactory.getExternal();
        switch (range) {
            case R10MIN:
                errors = dao.getErrorCountBy10Min(complex.getId(), bpInterfaceName);
                sdfForPrint = SDF_PATTERN_HR_MIN;
                break;
            case HOUR:
                errors = dao.getErrorCountByHour(complex.getId(), LocalDate.fromDateFields(dates[0]), bpInterfaceName);
                sdfForPrint = SDF_PATTERN_HR_MIN;
                break;
            case DAY:
                errors = dao.getErrorCountByDay(complex.getId(), LocalDate.fromDateFields(dates[0]), LocalDate.fromDateFields(dates[1]), bpInterfaceName);
                sdfForPrint = SDF_PATTERN_YEAR_MONTH_DAY;
                break;
            case MONTH:
                errors = dao.getErrorCount6Month(complex.getId(), bpInterfaceName);
                sdfForPrint = SDF_PATTERN_YEAR_MONTH;
                break;
        }
        return errors;
    }

    public void loadPaymentsErrors(FilterRange range, DInterfaceI bpInterface, Date[] dates) {
        List<ErrorCountI> paymentsErrors = loadErrors(Complex.BIPLANE_API2X, range, bpInterface, dates);
        if (paymentsErrors != null) {
            ChartData[] chartDataArray = processErrorsDbResult(paymentsErrors, sdfForPrint);
            chartData[0] = chartDataArray[0];
            chartData[1] = chartDataArray[1];
        }
    }

    public void loadTemplErrors(FilterRange range, DInterfaceI bpInterface, Date[] dates) {
        List<ErrorCountI> templErrors = loadErrors(Complex.TEMPLATES, range, bpInterface, dates);
        if (templErrors != null) {
            ChartData[] chartDataArray = processErrorsDbResult(templErrors, sdfForPrint);
            chartData[2] = chartDataArray[0];
            chartData[3] = chartDataArray[1];
        }
    }

    public ChartData getPaymentsBusinessErrors(FilterRange range, DInterfaceI bpInterface, Date[] dates, String interfaceKey) {
        return chartDataWrapper.getPaymentsBusinessErrors(range, bpInterface, dates, interfaceKey);
    }

    public ChartData getPaymentsSystemErrors(FilterRange range, DInterfaceI bpInterface, Date[] dates, String interfaceKey) {
        return chartDataWrapper.getPaymentsSystemErrors(range, bpInterface, dates, interfaceKey);
    }

    public ChartData getTemplBusinessErrors(FilterRange range, DInterfaceI bpInterface, Date[] dates, String interfaceKey) {
        return chartDataWrapper.getTemplBusinessErrors(range, bpInterface, dates, interfaceKey);
    }

    public ChartData getTemplSystemErrors(FilterRange range, DInterfaceI bpInterface, Date[] dates, String interfaceKey) {
        return chartDataWrapper.getTemplSystemErrors(range, bpInterface, dates, interfaceKey);
    }

    private ChartData[] processErrorsDbResult(List<ErrorCountI> paymentErrors, String sdf) {
        String[] paymentsCategories = new String[paymentErrors.size()];
        ListSeries p400errors = new ListSeries("4xx статус");
        ListSeries p500errors = new ListSeries("5xx статус");
        ListSeries p499errors = new ListSeries("499 статус");
        PlotOptionsColumn plot = new PlotOptionsColumn();
        plot.setColor(SolidColor.RED);
        p499errors.setPlotOptions(plot);
        int i = 0;
        for (ErrorCountI count : paymentErrors) {
            paymentsCategories[i++] = count.getDate().toString(sdf);
            p400errors.addData(count.getBusiness());
            p500errors.addData(count.getSystem());
            p499errors.addData(count.get499Code());
        }
        List<Series> payments400errors = new ArrayList<Series>();
        List<Series> payments500errors = new ArrayList<Series>();
        payments400errors.add(p400errors);
        payments500errors.add(p500errors);
        return new ChartData[]{new ChartData(paymentsCategories, payments400errors, p499errors),
                new ChartData(paymentsCategories, payments500errors)};
    }

    public Map<String, Integer> getErrCntByInterface(Complex complex, boolean isSystemErrorsChart, FilterRange filterRange, Calendar calendar) {
        Map<String, Integer> map;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        Integer day = null;
        if (filterRange != MONTH) {
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        Integer hour = null;
        if (filterRange == R10MIN || filterRange == HOUR) {
            hour = calendar.get(Calendar.HOUR_OF_DAY);
        }
        Integer minute = null;
        if (filterRange == R10MIN) {
            minute = calendar.get(Calendar.MINUTE);
        }
        map = ServiceFactory.getExternal().getQueryErrCnt(complex, isSystemErrorsChart, year, month, day, hour, minute);
        return map;
    }
}
