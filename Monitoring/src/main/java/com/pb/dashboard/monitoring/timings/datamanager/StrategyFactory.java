package com.pb.dashboard.monitoring.timings.datamanager;

import com.pb.dashboard.monitoring.components.filter.FilterRange;
import org.apache.log4j.Logger;

/**
 * Created by vlad
 * Date: 23.12.14_17:17
 */
public class StrategyFactory {

    private static final Logger log = Logger.getLogger(StrategyFactory.class);

    public static StrategyI build(FilterRange range, LoadData loadData) {
        if (range == null) {
            return new StrategyBy10Min(loadData);
        }
        switch (range) {
            case R10MIN:
                return new StrategyBy10Min(loadData);
            case HOUR:
                return new StrategyByHour(loadData);
            case DAY:
                return new StrategyByDay(loadData);
            default:
                log.warn("Strategy " + range.getName() + " not found");
                return new StrategyBy10Min(loadData);
        }
    }
}