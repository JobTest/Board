package com.pb.dashboard.vitrina.core.config.information;

public interface MetricsInf {

    public final static int KASSA_CASH_METRIC = 1;
    public final static int KASSA_NOCASH_METRIC = 2;
    public final static int KASSA_DEBT_METRIC = 3;
    public final static int BASS_CASH_METRIC = 10;
    public final static int BASS_NOCASH_METRIC = 11;
    public final static int BASS_DEBT_METRIC = 12;
    public final static int P24_NOCASH_METRIC = 17;
    public final static int P24_DEBT_METRIC = 18;
    public final static int REGULAR_RECEIVED = 23;
    public final static int REGULAR_PROCESSED = 24;
    public final static int DEBT_ACCEPTED = 39;
    public final static int DEBT_NOT_PROCESSED = 40;
    public final static int PVN_ACCEPTED = 41;
    public final static int PVN_PROCESSED = 42;
    public final static int DEPT_3700 = 74;    //Кол-во платежей по задолженности в точке приема '3700' (Украина)
    public final static int NON_CASH_3700 = 75;    //Кол-во безналичных платежей в точке приема '3700' (Украина)
    public final static int KASSA_URLIC = 76;    //Кол-во платежей юрлиц в кассе ПК Биплан (Украина)
    public final static int KASSA_FIZLIC = 77;    //Кол-во платежей физлиц в кассе ПК Биплан (Украина)
    public final static int BASS_URLIC = 78;    //Кол-во платежей юрлиц в BASS-терминалах (Украина)
    public final static int BASS_FIZLIC = 79;    //Кол-во платежей физлиц в BASS-терминалах (Украина)
    public final static int P24_URLIC = 80;    //Кол-во платежей юрлиц в ПК Приват 24 (Украина)
    public final static int P24_FIZLIC = 81;    //Кол-во платежей физлиц в ПК Приват 24 (Украина)
    public final static int URLIC_3700 = 82;    //Кол-во платежей юрлиц в 3700 (Украина)
    public final static int FIZLIC_3700 = 83;    //Кол-во платежей физлиц в 3700 (Украина)

    public final static int KASSA_CASH_METRIC_RU = 54;
    public final static int KASSA_NOCASH_METRIC_RU = 55;
    public final static int KASSA_DEBT_METRIC_RU = 56;
    public final static int BASS_CASH_METRIC_RU = 57;
    public final static int BASS_NOCASH_METRIC_RU = 58;
    public final static int BASS_DEBT_METRIC_RU = 59;
    public final static int P24_NOCASH_METRIC_RU = 60;
    public final static int P24_DEBT_METRIC_RU = 61;
    public final static int REG_DEBT_RU = 62;
    public final static int REG_NON_CASH_RU = 63;

    public final static int KASSA_URLIC_RU = 84;
    public final static int KASSA_FIZLIC_RU = 85;
    public final static int BASS_URLIC_RU = 86;
    public final static int BASS_FIZLIC_RU = 87;
    public final static int P24_URLIC_RU = 88;
    public final static int P24_FIZLIC_RU = 89;
    public final static int REG_URLIC_RU = 90;
    public final static int REG_FIZLIC_RU = 91;

    public final static int KASSA_CASH_METRIC_GEO = 64;
    public final static int KASSA_NOCASH_METRIC_GEO = 65;
    public final static int KASSA_DEBT_METRIC_GEO = 66;
    public final static int BASS_CASH_METRIC_GEO = 67;
    public final static int BASS_NOCASH_METRIC_GEO = 68;
    public final static int BASS_DEBT_METRIC_GEO = 69;
    public final static int P24_NOCASH_METRIC_GEO = 70;
    public final static int P24_DEBT_METRIC_GEO = 71;
    public final static int REG_DEBT_GEO = 72;
    public final static int REG_NON_CASH_GEO = 73;


    public final static int KASSA_URLIC_GEO = 92;
    public final static int KASSA_FIZLIC_GEO = 93;
    public final static int BASS_URLIC_GEO = 94;
    public final static int BASS_FIZLIC_GEO = 95;
    public final static int P24_URLIC_GEO = 96;
    public final static int P24_FIZLIC_GEO = 97;
    public final static int REG_URLIC_GEO = 98;
    public final static int REG_FIZLIC_GEO = 99;
}
