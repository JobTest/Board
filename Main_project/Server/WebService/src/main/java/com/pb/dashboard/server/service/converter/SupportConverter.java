package com.pb.dashboard.server.service.converter;

import com.pb.dashboard.server.dao.entity.iqlogrep.ErrorCountI;
import com.pb.dashboard.server.service.api.ErrorCountApi;
import com.pb.dashboard.server.service.api.ErrorCountApiI;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad
 * Date: 06.05.15_15:24
 */
public class SupportConverter {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static List<ErrorCountApiI> convert(List<ErrorCountI> errors) {
        List<ErrorCountApiI> res = new ArrayList<>();
        for (ErrorCountI error : errors) {
            res.add(convert(error));
        }
        return res;
    }

    public static ErrorCountApiI convert(ErrorCountI error) {
        ErrorCountApi api = new ErrorCountApi();
        api.setDate(error.getDate().toDate().getTime());
        api.setBusiness(error.getBusiness());
        api.setSystem(error.getSystem());
        api.setCode499(error.get499Code());
        return api;
    }
}
