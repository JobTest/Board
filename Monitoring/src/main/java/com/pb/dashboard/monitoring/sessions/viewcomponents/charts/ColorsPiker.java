package com.pb.dashboard.monitoring.sessions.viewcomponents.charts;

import com.vaadin.addon.charts.model.style.SolidColor;

import java.util.HashMap;
import java.util.Map;

public class ColorsPiker {

    private static final SolidColor[] COLORS = new SolidColor[]{
            new SolidColor(255, 102, 102),
            new SolidColor(255, 178, 102),
            new SolidColor(255, 255, 102),
            new SolidColor(178, 255, 102),
            new SolidColor(102, 255, 102),
            new SolidColor(102, 255, 178),
            new SolidColor(102, 255, 255),
            new SolidColor(102, 178, 255),
            new SolidColor(102, 102, 255),
            new SolidColor(178, 102, 255),
            new SolidColor(255, 102, 255),
            new SolidColor(255, 102, 178),
            new SolidColor(255, 102, 102)
    };
    private static final SolidColor GRAY = SolidColor.GRAY;

    public Map<Integer, SolidColor> getColors() {
        Map<Integer, SolidColor> colors = new HashMap<>();
        for (int i = 0; i < COLORS.length; i++) {
            colors.put(i + 1, COLORS[i]);
        }
        return colors;
    }

    public SolidColor getGray() {
        return GRAY;
    }
}
