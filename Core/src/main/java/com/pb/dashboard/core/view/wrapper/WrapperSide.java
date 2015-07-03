package com.pb.dashboard.core.view.wrapper;

/**
 * Created by vlad
 * Date: 16.01.15_14:09
 */
public enum WrapperSide {

    /**
     * @see com.vaadin.shared.ui.MarginInfo
     */
    TOP(1),
    RIGHT(2),
    BOTTOM(4),
    LEFT(8),
    RIGHT_LEFT(10);


    private final int code;

    WrapperSide(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
