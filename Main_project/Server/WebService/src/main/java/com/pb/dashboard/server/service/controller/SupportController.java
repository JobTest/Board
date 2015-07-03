package com.pb.dashboard.server.service.controller;

import com.pb.dashboard.server.dao.entity.iqlogrep.InterfaceErrorCountI;
import com.pb.dashboard.server.service.api.ErrorCountApiI;
import com.pb.dashboard.server.service.api.support.FilterType;
import com.pb.dashboard.server.service.api.support.FilterTypeI;
import com.pb.dashboard.server.service.businesslayer.SupportServiceI;
import com.pb.dashboard.server.service.converter.StringConvert;
import com.pb.dashboard.server.service.utils.HeaderUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vlad
 * Date: 05.05.15_16:56
 */

@Controller
@RequestMapping(value = "/support")
public class SupportController {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    @Autowired
    private SupportServiceI service;

    @RequestMapping(value = "/err-count-by-filter", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ErrorCountApiI> errorCountByFilter(@RequestParam("type_id") int typeId,
                                                   @RequestParam("complex_id") int complexId,
                                                   @RequestParam(value = "date", required = false) String date,
                                                   @RequestParam(value = "start_date", required = false) String startDate,
                                                   @RequestParam(value = "end_date", required = false) String endDate,
                                                   @RequestParam("interface") String bpInterface, HttpServletResponse response) {
        FilterType type = FilterType.getType(typeId);
        switch (type) {
            case T10MIN:
                return errorCountBy10Min(complexId, bpInterface, response);
            case HOUR:
                return errorCountByHour(complexId, date, bpInterface, response);
            case DAY:
                return errorCountByDay(complexId, startDate, endDate, bpInterface, response);
            case MONTH:
                return errorCountBy6Month(complexId, bpInterface, response);
        }
        throw new IllegalArgumentException("Type must be from 0 to 3.");
    }

    @RequestMapping(value = "/err-count-by-10min", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ErrorCountApiI> errorCountBy10Min(@RequestParam("complex_id") int complexId,
                                                  @RequestParam("interface") String bpInterface, HttpServletResponse response) {
        response.setHeader(HeaderUtils.EXPIRES, HeaderUtils.getDateMinuteGMT(LocalDateTime.now(DateTimeZone.UTC), 10));
        return service.getErrorCountBy10Min(complexId, StringConvert.convert(bpInterface));
    }

    @RequestMapping(value = "/err-count-by-hour", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ErrorCountApiI> errorCountByHour(@RequestParam("complex_id") int complexId,
                                                 @RequestParam("date") String date,
                                                 @RequestParam("interface") String bpInterface, HttpServletResponse response) {
        response.setHeader(HeaderUtils.EXPIRES, HeaderUtils.getDateHourGMT(LocalDateTime.now(DateTimeZone.UTC), 1));
        return service.getErrorCountByHour(complexId, parseDate(date), StringConvert.convert(bpInterface));
    }

    @RequestMapping(value = "/err-count-by-day", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ErrorCountApiI> errorCountByDay(@RequestParam("complex_id") int complexId,
                                                @RequestParam("start_date") String startDate,
                                                @RequestParam("end_date") String endDate,
                                                @RequestParam("interface") String bpInterface, HttpServletResponse response) {
        response.setHeader(HeaderUtils.EXPIRES, HeaderUtils.getDateDayGMT(LocalDate.now(DateTimeZone.UTC), 1));
        return service.getErrorCountByDay(complexId, parseDate(startDate), parseDate(endDate), StringConvert.convert(bpInterface));
    }

    @RequestMapping(value = "/err-count-6-month", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ErrorCountApiI> errorCountBy6Month(@RequestParam("complex_id") int complexId,
                                                   @RequestParam("interface") String bpInterface, HttpServletResponse response) {
        response.setHeader(HeaderUtils.EXPIRES, HeaderUtils.getDateDayGMT(LocalDate.now(DateTimeZone.UTC), 15));
        return service.getErrorCount6Month(complexId, StringConvert.convert(bpInterface));
    }

    @RequestMapping(value = "/filters", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<FilterTypeI> filters(HttpServletResponse response) {
        response.setHeader(HeaderUtils.EXPIRES, HeaderUtils.getDateDayGMT(LocalDate.now(DateTimeZone.UTC), 15));
        return new ArrayList<FilterTypeI>(Arrays.asList(FilterType.values()));
    }

    @RequestMapping(value = "/interface-error-counts", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<InterfaceErrorCountI> interfaceErrorCounts(@RequestParam("complex_id") int complexId,
                                                           @RequestParam("system_error") boolean systemError,
                                                           @RequestParam("date") String date,
                                                           @RequestParam(value = "hour", required = false) Integer hour,
                                                           @RequestParam(value = "minute", required = false) Integer minute,
                                                           HttpServletResponse response) {
        response.setHeader(HeaderUtils.EXPIRES, HeaderUtils.getDateMinuteGMT(LocalDateTime.now(DateTimeZone.UTC), 10));
        return service.getInterfaceErrorCounts(complexId, systemError, parseDate(date), hour, minute);
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        PropertyEditor editor = new CustomDateEditor(new SimpleDateFormat(DATE_PATTERN), true);
        binder.registerCustomEditor(LocalDateTime.class, editor);
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormat.forPattern(DATE_PATTERN));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Date[" + date + "] incorrect. Format is " + DATE_PATTERN);
        }
    }
}