package com.pb.dashboard.server.service.controller;

import com.pb.dashboard.server.service.api.AuthPay;
import com.pb.dashboard.server.service.api.SmsPay;
import com.pb.dashboard.server.service.businesslayer.MassPaysServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by vlad
 * Date: 03.03.15_16:49
 */

@Controller
@RequestMapping(value = "/masspay")
public class MassPayController {

    @Autowired
    private MassPaysServiceI massPays;

    @RequestMapping(value = "/auth-pays", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<AuthPay> getAuthPays() {
        return massPays.getAuthPays();
    }

    @RequestMapping(value = "/sms-pays", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<SmsPay> getSmsPays() {
        return massPays.getSmsPays();
    }
}