package com.pb.dashboard.monitoring.components.filter;

import com.pb.dashboard.core.util.IntegerUtil;

public enum FilterRange {

    R10MIN(0, "10 мин"),
    HOUR(1, "час"),
    DAY(2, "день");

    private int pkey;
    private String name;

    private FilterRange(int pkey, String name) {
        this.pkey = pkey;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPkey() {
        return pkey;
    }

    public static FilterRange pkeyToFilterRange(String pkeyStr) {
        if (IntegerUtil.checkInt(pkeyStr)) {
            int pkey = Integer.parseInt(pkeyStr);
            return pkeyToFilterRange(pkey);
        }
        throw new IllegalArgumentException("FilterRange with pkey [" + pkeyStr + "] not exists");
    }

    public static FilterRange pkeyToFilterRange(int pkey) {
        for (FilterRange range : FilterRange.values()) {
            if (range.getPkey() == pkey) {
                return range;
            }
        }
        throw new IllegalArgumentException("FilterRange with pkey [" + pkey + "] not exists");
    }
}
