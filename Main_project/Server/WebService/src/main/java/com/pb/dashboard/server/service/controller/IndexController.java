package com.pb.dashboard.server.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Created by vlad
 * Date: 08.01.15_16:05
 */
@Controller
public class IndexController {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView mainPage() {
        return new ModelAndView("index.jsp");
    }

    @RequestMapping(value = "/points", method = RequestMethod.GET)
    public String getEndPointsInView(Model model) {
        model.addAttribute("endPoints", requestMappingHandlerMapping.getHandlerMethods().keySet());
        return "points";
    }
}