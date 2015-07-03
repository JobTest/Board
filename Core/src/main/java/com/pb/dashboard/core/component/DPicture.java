package com.pb.dashboard.core.component;

import java.io.File;

/**
 * Created by vlad
 * Date: 10.10.14_8:44
 */
public class DPicture {

    public enum DASH_IMG {
        BACK_BUTTON_WHITE("back-button-white.png"),
        MAIN_BUTTON_WHITE("main-button-white.png");
        private static final String PATH = "dash-img";
        private final String name;

        private DASH_IMG(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return PATH + File.separator + name;
        }
    }

    public enum Png {
        UKR_FLAG("timings/ukr-flag.png"),
        RUS_FLAG("timings/rus-flag.png"),
        GEO_FLAG("timings/geo-flag.png"),
        LINK("link.png");

        private static final String PATH = "img";
        private final String name;

        private Png(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return PATH + File.separator + name;
        }
    }
}