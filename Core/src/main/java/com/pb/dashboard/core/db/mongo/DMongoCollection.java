package com.pb.dashboard.core.db.mongo;

/**
 * Created by vlad
 * Date: 05.12.14_10:07
 */
public enum DMongoCollection {

    API_STACKTRACE("api_stacktrace"),
    API_TIMING_T0("api_timing_t0"),
    API_TIMING_T2("api_timing_t2"),
    API_XML_T0("api_xml_t0"),
    API_XML_T1("api_xml_t1"),
    DEBT_XML_T0("debt_xml_t0"),
    DEBT_XML_T1("debt_xml_t1"),
    TEMP_STACKTRACE("temp_stacktrace"),
    TEMP_TIMING_T0("temp_timing_t0"),
    TEMP_TIMING_T2("temp_timing_t2"),
    TEMP_XML_T0("temp_xml_t0");

    private String name;

    private DMongoCollection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
