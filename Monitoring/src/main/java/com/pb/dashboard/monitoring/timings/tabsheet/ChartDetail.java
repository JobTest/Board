package com.pb.dashboard.monitoring.timings.tabsheet;

import com.pb.dashboard.core.component.DPopupWindow;
import com.pb.dashboard.core.util.StringUtil;
import com.pb.dashboard.monitoring.timings.transferlink.LinkManagerI;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Series;
import com.vaadin.server.ExternalResource;
import org.joda.time.*;
import org.vaadin.activelink.ActiveLink;

import java.util.List;

/**
 * Created by vlad
 * Date: 03.04.15_16:17
 */
public class ChartDetail {

    private static final String DETAILS = "Подробнее";
    private DPopupWindow popup = new DPopupWindow();
    private LinkManagerI linkManager;

    private LocalDateTime fromDateTime = DateTime.now().toLocalDateTime();
    private LocalDateTime toDateTime = DateTime.now().toLocalDateTime();

    public ChartDetail(LinkManagerI linkManager) {
        this.linkManager = linkManager;
    }

    public void showPopup(String nameInterface, int x, int y) {
        String path = getPath(fromDateTime, toDateTime, nameInterface);

        if (StringUtil.isEmptyOrNull(path)) {
            return;
        }
        path = "#!" + path;

        ActiveLink link = new ActiveLink(DETAILS, new ExternalResource(path));
        popup.open(x, y, link);
    }

    public void updatePeriod(Series series, String category) {
        List<DataSeriesItem> items = ((DataSeries) series).getData();
        long time = Long.parseLong(category);
        for (int i = 0; i < items.size(); i++) {
            Number nowX = items.get(i).getX();
            if (!nowX.equals(time)) {
                continue;
            }
            fromDateTime = new LocalDateTime(nowX, DateTimeZone.UTC);
            if (items.size() > i + 1) {
                Number nextX = items.get(i + 1).getX();
                toDateTime = new LocalDateTime(nextX, DateTimeZone.UTC);
            } else if (i >= 1) {
                Number prevX = items.get(i - 1).getX();
                LocalDateTime dateTime = new LocalDateTime(prevX, DateTimeZone.UTC);

                Minutes minutes = Minutes.minutesBetween(dateTime, fromDateTime);
                toDateTime = fromDateTime.plus(minutes);
            }
        }
    }

    private String getPath(LocalDateTime fromDateTime, LocalDateTime toDateTime, String nameInterface) {
        if (linkManager != null) {
            return linkManager.pathToSessions(fromDateTime, toDateTime, nameInterface);
        }
        return "";
    }

    public void setDefault(LocalDate fromDate, LocalDate toDate) {
        this.fromDateTime = fromDate.toLocalDateTime(new LocalTime(0, 0));
        this.toDateTime = toDate.toLocalDateTime(new LocalTime(0, 0));
    }

    public LocalDateTime getFromDateTime() {
        return fromDateTime;
    }

    public LocalDateTime getToDateTime() {
        return toDateTime;
    }
}
