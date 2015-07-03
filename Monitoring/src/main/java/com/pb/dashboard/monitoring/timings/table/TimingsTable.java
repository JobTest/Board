package com.pb.dashboard.monitoring.timings.table;

import com.pb.dashboard.core.util.IntegerUtil;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.pb.dashboard.monitoring.timings.TimingsType;
import com.pb.dashboard.monitoring.timings.format.FormatterCalendar;
import com.vaadin.ui.Table;
import org.joda.time.LocalDateTime;

import java.util.List;

/**
 * Created by vlad
 * Date: 08.12.14_17:19
 */
public class TimingsTable extends Table {

    public static final String TOTAL = "Всего";

    public static final int COUNT_COLUMN = 9;
    public static final int PERIOD_INDEX = 0;
    public static final String PERIOD = "Период";

    private static final int FIRST_INT_COLUMN = 1;
    private static final long serialVersionUID = -6387575354803349604L;
    private int[] total = new int[COUNT_COLUMN];
    private FilterRange filterRange;

    public TimingsTable() {
        setWidth("100%");
        setPageLength(0);
        setSortEnabled(false);
        setFooterVisible(true);
        setColumnCollapsingAllowed(true);
        setColumnCollapsible(PERIOD, false);
        setColumnCollapsible(TimingsType.QUERIES.getName(), false);
        setColumnCollapsible(TimingsType.MAX.getName(), false);

        setColumnAlignment(PERIOD, Table.Align.LEFT);

        addContainerProperty(PERIOD, String.class, null);
        for (TimingsType type : TimingsType.values()) {
            addContainerProperty(type.getName(), Integer.class, null);
        }
        updateTotal();
    }

    public void setItems(List<MetricItem> metricItems) {
        removeAllItems();
        clearTotal();
        for (MetricItem metric : metricItems) {
            LocalDateTime dateTime = metric.getDateTime();
            String formatDate = format(dateTime);
            addItem(formatDate, metric);
        }
        updateTotal();
    }

    public Object addItem(String itemId, MetricItem metric) {
        Object[] cells = getCells(itemId, metric);
        for (int i = FIRST_INT_COLUMN; i < cells.length; i++) {
            if (IntegerUtil.checkInt(cells[i])) {
                int ch = Integer.parseInt(cells[i].toString());
                total[i] += ch;
            }
        }
        return super.addItem(cells, itemId);
    }

    private Object[] getCells(Object itemId, MetricItem metric) {
        Object[] cells = new Object[]{
                itemId,
                metric.getCount(),
                metric.getErrorCount(),
                metric.getMin(),
                metric.getMax(),
                metric.getAvg(),
                metric.getT90(),
                metric.getT95(),
                metric.getT99()
        };
        return cells;
    }

    public void updateTotal() {
        setColumnFooter(PERIOD, TOTAL);
        int i = PERIOD_INDEX;
        for (Object obj : getColumnHeaders()) {
            if (i != PERIOD_INDEX) {
                setColumnFooter(obj, String.valueOf(total[i]));
            }
            i++;
        }
    }

    private void clearTotal() {
        for (int i = FIRST_INT_COLUMN; i < total.length; i++) {
            total[i] = 0;
        }
        updateTotal();
    }

    private String format(LocalDateTime dateTime) {
        if (filterRange == null) {
            filterRange = FilterRange.R10MIN;
        }
        return FormatterCalendar.format(filterRange, dateTime);
    }

    public void setFormatterCalendar(FilterRange filterRange) {
        this.filterRange = filterRange;
    }
}