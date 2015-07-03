package com.pb.dashboard.monitoring.timings.transferlink;

import org.joda.time.LocalDateTime;

/**
 * Created by vlad
 * Date: 22.12.14_10:46
 */
public interface LinkManagerI {

    public String urlParamsSessions(LocalDateTime fromDateTime, LocalDateTime toDateTime, String category);

    public String pathToTimings();

    public String urlParamsTimings();

    public String pathToSessions(LocalDateTime fromDateTime, LocalDateTime toDateTime, String category);
}
