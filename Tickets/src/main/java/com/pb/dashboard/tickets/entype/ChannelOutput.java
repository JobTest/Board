package com.pb.dashboard.tickets.entype;

/**
 * Created by vlad
 * Date: 02.12.14_17:03
 */
public class ChannelOutput {

    private Integer pkey;
    private String name;

    public ChannelOutput(Integer pkey, String name) {
        this.pkey = pkey;
        this.name = name;
    }

    public Integer getPkey() {
        return pkey;
    }

    public String getName() {
        return name;
    }
}
