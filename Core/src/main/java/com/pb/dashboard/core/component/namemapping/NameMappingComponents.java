package com.pb.dashboard.core.component.namemapping;

/**
 * Created by evasive on 12.11.14.
 */

public class NameMappingComponents {

    public enum TestTextField implements ComponentNameI {

        SEARCH,
        PASSWORD;

        private String strTextField = "text_field_";

        @Override
        public String getName() {
            return strTextField + name().toLowerCase();
        }
    }

    public enum TestButton implements ComponentNameI {

        APPLY,
        ERRORS_API,
        ERRORS_TEMP,
        ERRORS_DEBT;

        private String strTestButton = "button_";

        @Override
        public String getName() {
            return   strTestButton+ name().toLowerCase();
        }
    }

    public enum TestLink implements ComponentNameI {
        TICKETS,
        BIPLANE,
        TEMPLATES,
        QUALITY;

        private String strTestLink = "link_";

        @Override
        public String getName() {
            return strTestLink + name().toLowerCase();
        }
    }


}
