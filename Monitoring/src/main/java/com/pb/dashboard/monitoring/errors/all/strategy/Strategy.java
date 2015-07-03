package com.pb.dashboard.monitoring.errors.all.strategy;

/**
 * Created by vlad
 * Date: 13.01.15_9:47
 */
public final class Strategy {

    private Strategy() {
    }

    public static void transitionOnPage(PanelModelI model) {
        switch (model.getType()) {
            case ERROR:
                model.setType(PageType.RECIPIENT);
                break;
            case RECIPIENT:
                model.setType(PageType.ERROR);
        }
        switch (model.getNumber()) {
            case FIRST:
                model.setNumber(PageNumber.SECOND);
                break;
            case SECOND:
                model.setNumber(PageNumber.FIRST);
        }
    }
}