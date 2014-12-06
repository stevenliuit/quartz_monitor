package com.jttx.pj.controller;

import com.jttx.pj.entity.Enterprise;
import com.jttx.pj.entity.Role;
import com.jttx.pj.entity.User;
import com.jttx.pj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by yamorn on 2014/12/6.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/EntRegister", method = RequestMethod.GET)
    public ModelAndView register(ModelAndView modelAndView) {
        User user = new User();
        user.getRoles().add(new Role());
        user.setEnterprise(new Enterprise());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/public/entRegister");
        return modelAndView;
    }

    @RequestMapping(value = "/registerAction", method = RequestMethod.POST)
    public String registerAction(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println("Validation error: " + error.getDefaultMessage());
            }
            return "/public/entRegister";
        }
        userService.save(user);
        session.setAttribute("user", user);
        return "index";
    }

}
