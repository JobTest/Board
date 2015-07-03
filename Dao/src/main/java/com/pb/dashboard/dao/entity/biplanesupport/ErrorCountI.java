package com.pb.dashboard.dao.entity.biplanesupport;

import org.joda.time.LocalDateTime;

/**
 * Created by vlad
 * Date: 10.04.15_10:20
 */
public interface ErrorCountI {

    LocalDateTime getDate();

    int getBusiness();

    int getSystem();

    int get499Code();

}
