package com.pb.dashboard.core.component.navigation;

import com.pb.dashboard.core.component.DPicture;

/**
 * Created by vlad
 * Date: 10.10.14_8:35
 */
public enum NavigationBarType {

    BACK(DPicture.DASH_IMG.BACK_BUTTON_WHITE.toString()),
    MAIN(DPicture.DASH_IMG.MAIN_BUTTON_WHITE.toString());

    private String picture;

    private NavigationBarType(String picture) {
        this.picture = picture;
    }


    public String getPicture() {
        return picture;
    }
}
