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
        return "public/index";
    }
    @RequestMapping("/403")
    public String error403() {
        return "error/403";
    }
}
