package com.pb.dashboard.server.service.controller;

import com.pb.dashboard.server.dao.entity.vitrina.BpInterfaceI;
import com.pb.dashboard.server.service.api.CompanyDescription;
import com.pb.dashboard.server.service.api.CompanyItem;
import com.pb.dashboard.server.service.api.RecipientSla;
import com.pb.dashboard.server.service.businesslayer.MonitoringServiceI;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vlad
 * Date: 23.01.15_15:57
 */

@Controller
@RequestMapping(value = "/monitoring")
public class MonitoringController {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    @Autowired
    private MonitoringServiceI monitoring;

    @RequestMapping(value = "/recipient-sla", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<RecipientSla> getRecipientSla(@RequestParam("interface") String interfaceName,
                                              @RequestParam("date") String date,
                                              @RequestParam("top") int top) {
        return monitoring.getRecipientSla(interfaceName, parseDate(date), top);
    }

    @RequestMapping(value = "/company-items", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<CompanyItem> getCompanyItems(@RequestParam("interface") String interfaceName,
                                             @RequestParam("date") String date,
                                             @RequestParam("company_id") int companyId) {
        return monitoring.getCompanyItems(interfaceName, parseDate(date), companyId);
    }

    @RequestMapping(value = "/company-description", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public CompanyDescription getCompanyDescription(@RequestParam("company_id") Integer companyId) {
        return monitoring.getCompanyDescription(companyId);
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormat.forPattern(DATE_PATTERN));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Date[" + date + "] incorrect. Format is " + DATE_PATTERN);
        }
    }
}