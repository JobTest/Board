package com.pb.dashboard.vitrina.core.country;

public interface CountryI {
    //Kassa
    int getKassaCash();

    int getKassaNonCash();

    int getKassaDebt();

    int getKassaFizLic();

    int getKassaUrLic();

    //Bass
    int getBassCash();

    int getBassNonCash();

    int getBassDebt();

    int getBassFizLic();

    int getBassUrLic();

    //p24
    int getP24NonCash();

    int getP24Debt();

    int getP24FizLic();

    int getP24UrLic();

    //3700
    int get3700NonCash();

    int get3700Debt();

    int get3700FizLic();

    int get3700UrLic();

}
