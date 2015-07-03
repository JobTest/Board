package com.pb.dashboard.tickets.entype;

public enum Branch {

    UNDEF(-1, "НЕ ОПРЕДЕЛЕНО", "Undefined"),
    NORTH_WEST(1, "СУМСКОЙ Ф-Л", "SUH0"),
    KHARKOV(2, "ХАРЬКОВСКОЕ ГРУ", "HAH0"),
    CHERNIGOV(3, "ЧЕРНИГОВСКОЕ РУ", "CNH0"),
    SUMI(4, "СЕВЕРО-ЗАПАДНОЕ РУ", "VOH0"),
    ZHITOMIR(5, "ЖИТОМИРСКОЕ РУ", "ZRH0"),
    POLTAVA(6, "ПОЛТАВСКОЕ ГРУ", "PLH0"),
    CHERKASSI(7, "ЧЕРКАССКОЕ ГРУ", "CSH0"),
    STOLICHNIY(8, "СТОЛИЧНЫЙ Ф-Л", "K5H0"),
    KRAMATORSK(9, "КРАМАТОРСКИЙ Ф-Л", "KTH0"),
    ABANK(10, "А-БАНК", "ABH0"),
    MARIUPOL(11, "МАРИУПОЛЬСКИЙ Ф-Л", "MRH0"),
    SOUTH_WEST(12, "ЮГО-ЗАПАДНОЕ РУ", "HMH0"),
    KIROVOGRAD(13, "КИРОВОГРАДСКОЕ РУ", "KGH0"),
    PECHERSKIY_KIEV(14, "ПЕЧЕРСКИЙ Ф-Л", "K3H0"),
    ACCOUNTING_CENTER_KIEV(15, "РАСЧЕТНЫЙ ЦЕНТР", "K2H0"),
    KIEV_CITY(16, "Ф-Л \"КИЕВСИТИ\"", "K4H0"),
    KIEV_GRU(17, "КИЕВСКОЕ ГРУ", "KIH0"),
    WEST_LV(18, "ЗАПАДНОЕ ГРУ", "LVH0"),
    DONETSK(19, "ДОНЕЦКОЕ РУ", "DOH0"),
    VINNITSA(20, "ВИННИЦКИЙ Ф-Л", "VIH0"),
    SOUTH_OD(21, "ЮЖНОЕ ГРУ", "ODH0"),
    KRIVY_RIG(22, "КРИВОРОЖСКИЙ Ф-Л", "KRH0"),
    NIKOLAEV(23, "НИКОЛАЕВСКОЕ РУ", "NKH0"),
    LUGANSK(24, "ЛУГАНСКИЙ Ф-Л", "LGH0"),
    SOUTH_EAST_ZP(25, "ЮГО-ВОСТОЧНОЕ РУ", "ZPH0"),
    DNEPROPETROVSK(26, "ДНЕПРОПЕТРОВСКОЕ РУ", "DNF0"),
    KREMENCHUG(27, "КРЕМЕНЧУГСКИЙ Ф-Л", "PLR0"),
    HEADQUARTERS(28, "ГОЛОВНОЙ БАНК", "DNH0"),

    KRUM(29, "КРЫМСКОЕ РУ (Г. СИМФЕРОПОЛЬ)", "SIH0"),
    ALCHEVSK(30, "АЛЧЕВСКИЙ ФИЛИАЛ", "AKH0"),
    CHERVONOGRAD(31, "ЧЕРВОНОГРАДСКИЙ ФИЛИАЛ", "CGH0"),
    GO(32, "ГОЛОВНОЙ БАНК", "DN1F"),
    GORLOVSKIY(33, "ГОРЛОВСКИЙ ФИЛИАЛ", "GOH0"),
    SEVASTOPOL(34, "СЕВАСТОПОЛЬСКИЙ ФИЛИАЛ", "SEH0"),
    BERDANSK(35, "БЕРДЯНСКИЙ ФИЛИАЛ", "ZPB1");


    private int id;
    private String name;
    private String attrId;

    private Branch(int id, String name, String attrId) {
        this.id = id;
        this.name = name;
        this.attrId = attrId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAttrId() {
        return attrId;
    }

    public static Branch attrIdToBranch(String attrId) {
        for (Branch branch : Branch.values()) {
            if (branch.getAttrId().equals(attrId)) return branch;
        }
        return Branch.UNDEF;
    }
}
