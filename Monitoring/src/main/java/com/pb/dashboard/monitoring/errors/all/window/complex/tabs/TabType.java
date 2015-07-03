package com.pb.dashboard.monitoring.errors.all.window.complex.tabs;

import com.pb.dashboard.core.model.Complex;

public enum TabType {
    QUERY("Запрос", true, true, true),
    ANSWER("Ответ", true, true, true),
    TIMINGS("Тайминги", true, false, true),
    T0("T0", false, true, false),
    STACK_TRACE("StackTrace", true, false, true),
    ERRORS("Errors", true, false, false),
    TIME("Время", true, true, true);

    private final String name;
    private final boolean visibleApi;
    private final boolean visibleDebt;
    private final boolean visibleTemp;

    private TabType(String name, boolean visibleApi, boolean visibleDebt, boolean visibleTemp) {
        this.name = name;
        this.visibleApi = visibleApi;
        this.visibleDebt = visibleDebt;
        this.visibleTemp = visibleTemp;
    }

    public String getName() {
        return name;
    }

    public boolean isVisible(Complex complex) {
        switch (complex) {
            case BIPLANE_API2X:
                return visibleApi;
            case DEBT:
                return visibleDebt;
            case TEMPLATES:
                return visibleTemp;
        }
        return false;
    }

    public static TabType nameToComplexTab(String name) {
        for (TabType tab : values()) {
            if (tab.getName().equals(name)) {
                return tab;
            }
        }
        throw new IllegalArgumentException("TabType[name=" + name + "] is not exists.");
    }
}
