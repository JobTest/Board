package com.pb.dashboard.vitrina.core.config;

import java.io.Serializable;

public class ConfigManager implements Serializable {

    private static final long serialVersionUID = -6781860556937251746L;
    private static ConfigManager manager;
    private String redmine_host;// = "http://10.1.99.58/predmine";
    private String redmine_apikey;// = "46a4f326da314ddcb707ed41429450122446f786";
    private String jenkinsAddres;// = "http://10.13.71.175:8095/jenkins";
    private String appmanAdres;// = "http://10.13.71.90:9090";
    private String nagiosAddres;// = "http://10.13.99.72/nagios";
    private Language lang = Language.UA;

    public ConfigManager() {
        set();
    }

    public void set() {
        redmine_host = System.getProperty("com.pb.vitrina.redmine.host");
        redmine_apikey = System.getProperty("com.pb.vitrina.redmine.apikey");
        jenkinsAddres = System.getProperty("com.pb.vitrina.monitor.jenkinsAddr");
        appmanAdres = System.getProperty("com.pb.vitrina.monitor.appmanAddr");
        nagiosAddres = System.getProperty("com.pb.vitrina.monitor.nagiosAddr");
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }
}
