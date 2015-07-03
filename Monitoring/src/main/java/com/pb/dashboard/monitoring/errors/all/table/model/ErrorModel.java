package com.pb.dashboard.monitoring.errors.all.table.model;

/**
 * Created by vlad
 * Date: 11.09.14
 */
public class ErrorModel {

    private String code;
    private int count;
    private String decoding;

    public ErrorModel() {
    }

    public ErrorModel(String code, int count, String decoding) {
        this.code = code;
        this.count = count;
        this.decoding = decoding;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDecoding() {
        return decoding;
    }

    public void setDecoding(String decoding) {
        this.decoding = decoding;
    }
}
