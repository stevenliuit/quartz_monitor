package com.jttx.pj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by louis on 2014/12/6.
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
        public String index() {
        return "index";
    }
    @RequestMapping("/expire")
    public String expire() {
        return "index";
    }
    @RequestMapping("/403")
    public String error403() {
        return "error/403";
    }
    @RequestMapping("/404")
    public String error404() {
        return "error/404";
    }
    @RequestMapping("/500")
    public String error500() {
        return "error/500";
    }
}
