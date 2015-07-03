package com.pb.dashboard.vitrina.core.country;

import com.pb.dashboard.vitrina.core.config.information.MetricsInf;

public class CountryUA implements CountryI {

    @Override
    public int getKassaCash() {
        return MetricsInf.KASSA_CASH_METRIC;
    }

    @Override
    public int getKassaNonCash() {
        return MetricsInf.KASSA_NOCASH_METRIC;
    }

    @Override
    public int getKassaDebt() {
        return MetricsInf.KASSA_DEBT_METRIC;
    }

    @Override
    public int getKassaFizLic() {
        return MetricsInf.KASSA_FIZLIC;
    }

    @Override
    public int getKassaUrLic() {
        return MetricsInf.KASSA_URLIC;
    }

    @Override
    public int getBassCash() {
        return MetricsInf.BASS_CASH_METRIC;
    }

    @Override
    public int getBassNonCash() {
        return MetricsInf.BASS_NOCASH_METRIC;
    }

    @Override
    public int getBassDebt() {
        return MetricsInf.BASS_DEBT_METRIC;
    }

    @Override
    public int getBassFizLic() {
        return MetricsInf.BASS_FIZLIC;
    }

    @Override
    public int getBassUrLic() {
        return MetricsInf.BASS_URLIC;
    }

    @Override
    public int getP24NonCash() {
        return MetricsInf.P24_NOCASH_METRIC;
    }

    @Override
    public int getP24Debt() {
        return MetricsInf.P24_DEBT_METRIC;
    }

    @Override
    public int getP24FizLic() {
        return MetricsInf.P24_FIZLIC;
    }

    @Override
    public int getP24UrLic() {
        return MetricsInf.P24_URLIC;
    }

    @Override
    public int get3700NonCash() {
        return MetricsInf.NON_CASH_3700;
    }

    @Override
    public int get3700Debt() {
        return MetricsInf.DEPT_3700;
    }

    @Override
    public int get3700FizLic() {
        return MetricsInf.FIZLIC_3700;
    }

    @Override
    public int get3700UrLic() {
        return MetricsInf.URLIC_3700;
    }

}
