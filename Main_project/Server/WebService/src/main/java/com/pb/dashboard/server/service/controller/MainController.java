package com.pb.dashboard.server.service.controller;

import com.pb.dashboard.server.dao.entity.vitrina.BpInterfaceI;
import com.pb.dashboard.server.service.businesslayer.MainServiceI;
import com.pb.dashboard.server.service.utils.HeaderUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by vlad
 * Date: 12.05.15_15:08
 */
@Controller
@RequestMapping(value = "/main")
public class MainController {

    @Autowired
    private MainServiceI service;

    @RequestMapping(value = "/interfaces", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<BpInterfaceI> interfaces(@RequestParam("complex_id") int complexId,
                                         @RequestParam("country_id") int countryId, HttpServletResponse response) {
        response.setHeader(HeaderUtils.EXPIRES, HeaderUtils.getDateDayGMT(LocalDate.now(DateTimeZone.UTC), 5));
        return service.getBpInterfaces(complexId, countryId);
    }
}