package com.pb.dashboard.monitoring.errors.all.table;

import com.pb.dashboard.core.util.StringUtil;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDetailI;
import com.pb.dashboard.monitoring.errors.all.db.container.InfoError;
import com.vaadin.ui.Table;

import java.util.List;

public class InfoErrorTable extends Table {

    private static final String TEXT_RU = "Текст ошибки(рус.)";
    private static final String REASON = "Причина";
    private static final String RECOMMENDATION = "Рекомендация";
    private static final String GROUP = "Группа ошибок";
    private static final String RESPONSIBLE = "Ответственный";
    private static final String SYSTEM = "Система";
    private static final String TYPE = "Тип";

    public InfoErrorTable() {
        init();
    }

    private void init() {
        initStyle();
        setProperties();
        setAlignments();
        setColumnCollapses();
        setExpandRatios();
    }

    private void initStyle() {
        addStyleName("monitoring");
        setPageLength(0);
        setWidth("100%");
        setFooterVisible(true);
    }

    private void setProperties() {
        addContainerProperty(TEXT_RU, String.class, null);
        addContainerProperty(REASON, String.class, null);
        addContainerProperty(RECOMMENDATION, String.class, null);
        addContainerProperty(GROUP, String.class, null);
        addContainerProperty(RESPONSIBLE, String.class, null);
        addContainerProperty(SYSTEM, String.class, null);
        addContainerProperty(TYPE, String.class, null);
    }

    private void setAlignments() {
        setColumnAlignments(
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER);
    }

    private void setColumnCollapses() {
        setColumnCollapsingAllowed(true);
        setColumnCollapsed(GROUP, true);
        setColumnCollapsed(RESPONSIBLE, true);
        setColumnCollapsed(SYSTEM, true);
        setColumnCollapsed(TYPE, true);
        setColumnCollapsible(REASON, true);

        setColumnCollapsible(TEXT_RU, false);
        setColumnCollapsible(REASON, false);
        setColumnCollapsible(RECOMMENDATION, false);
    }

    private void setExpandRatios() {
        setColumnExpandRatio(TEXT_RU, 1.5f);
        setColumnExpandRatio(REASON, 3f);
        setColumnExpandRatio(RECOMMENDATION, 10f);
        setColumnExpandRatio(GROUP, 0.8f);
        setColumnExpandRatio(RESPONSIBLE, 1f);
        setColumnExpandRatio(SYSTEM, 1f);
        setColumnExpandRatio(TYPE, 1f);
    }

    public void setData(InfoError data) {
        removeAllItems();
        addItem(new Object[]{
                data.getTextRu(),
                data.getReason(),
                data.getRecommend(),
                data.getGr(),
                data.getResponsible(),
                data.getSystem(),
                data.getType()
        }, null);
    }

    public void setErrorDetail(ErrorDetailI error) {
        if (error == null) {
            return;
        }
        removeAllItems();
        addItem(new Object[]{
                notNull(error.getTextRu()),
                notNull(error.getReason()),
                notNull(error.getRecommend()),
                notNull(error.getGr()),
                notNull(error.getResponsible()),
                notNull(error.getSystem()),
                notNull(error.getType())
        }, null);
    }

    private String notNull(String string) {
        if (StringUtil.isEmptyOrNull(string)) {
            return "-";
        }
        return string;
    }
}
