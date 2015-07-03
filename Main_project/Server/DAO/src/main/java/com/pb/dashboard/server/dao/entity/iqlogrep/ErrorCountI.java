package com.pb.dashboard.server.dao.entity.iqlogrep;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by vlad
 * Date: 10.04.15_10:20
 */
public interface ErrorCountI {

    @JsonSerialize
    LocalDateTime getDate();

    int getBusiness();

    int getSystem();

    int get499Code();

}
