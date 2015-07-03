package com.pb.dashboard.external.charts.components;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;

public class CountryLink extends Link {
    private String caption = "Изменить страну на: ";
    private String current;
    private int bank;

    public CountryLink(String current) {
        this.current = current;
        String URL = getURL();
        if (bank == 0){
            caption += "Россия";
        }
        if (bank == 1){
            caption += "Украина";
        }
        setCaption(caption);
        setResource(new ExternalResource(URL));
    }

    public String getURL() {
        String URL="#";
        String[] splitedURL = current.split("/");

        int i = 0;
        while(!splitedURL[i].equals("charts")){
            URL += splitedURL[i]+"/";
            i++;
        }
        URL += splitedURL[i]+"/";
        bank = Integer.valueOf(splitedURL[i+1]);
        if (bank==0){
            URL += "1/";
        }
        if (bank == 1){
            URL += "0/";
        }
        for (int j = i+2; j < splitedURL.length; j++) {
            URL += splitedURL[j]+"/";
        }
        return URL;
    }
}
