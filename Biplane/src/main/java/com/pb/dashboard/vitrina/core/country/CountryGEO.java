package com.pb.dashboard.vitrina.core.country;

import com.pb.dashboard.vitrina.core.config.information.MetricsInf;

public class CountryGEO implements CountryI {
    @Override
    public int getKassaCash() {
        return MetricsInf.KASSA_CASH_METRIC_GEO;
    }

    @Override
    public int getKassaNonCash() {
        return MetricsInf.KASSA_NOCASH_METRIC_GEO;
    }

    @Override
    public int getKassaDebt() {
        return MetricsInf.KASSA_DEBT_METRIC_GEO;
    }

    @Override
    public int getKassaFizLic() {
        return MetricsInf.KASSA_FIZLIC_GEO;
    }

    @Override
    public int getKassaUrLic() {
        return MetricsInf.KASSA_URLIC_GEO;
    }

    @Override
    public int getBassCash() {
        return MetricsInf.BASS_CASH_METRIC_GEO;
    }

    @Override
    public int getBassNonCash() {
        return MetricsInf.BASS_NOCASH_METRIC_GEO;
    }

    @Override
    public int getBassDebt() {
        return MetricsInf.BASS_DEBT_METRIC_GEO;
    }

    @Override
    public int getBassFizLic() {
        return MetricsInf.BASS_FIZLIC_GEO;
    }

    @Override
    public int getBassUrLic() {
        return MetricsInf.BASS_URLIC_GEO;
    }

    @Override
    public int getP24NonCash() {
        return MetricsInf.P24_NOCASH_METRIC_GEO;
    }

    @Override
    public int getP24Debt() {
        return MetricsInf.P24_DEBT_METRIC_GEO;
    }

    @Override
    public int getP24FizLic() {
        return MetricsInf.P24_FIZLIC_GEO;
    }

    @Override
    public int getP24UrLic() {
        return MetricsInf.P24_URLIC_GEO;
    }

    @Override
    public int get3700NonCash() {
        return MetricsInf.REG_NON_CASH_GEO;
    }

    @Override
    public int get3700Debt() {
        return MetricsInf.REG_DEBT_GEO;
    }

    @Override
    public int get3700FizLic() {
        return MetricsInf.REG_FIZLIC_GEO;
    }

    @Override
    public int get3700UrLic() {
        return MetricsInf.REG_URLIC_GEO;
    }
}
