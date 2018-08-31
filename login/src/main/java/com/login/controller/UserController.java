package com.login.controller;


import com.login.model.Role;
import com.login.model.User;
import com.login.repository.UserRepository;
import com.login.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.soap.SOAPBinding;
import java.util.HashSet;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ModelAndView start() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("user") User user) {
        User user1 = userService.findByEmail(user.getEmail());
        ModelAndView modelAndView = new ModelAndView();
        if (!user1.getPassword() .equals(user.getPassword())) {
            modelAndView.addObject("msg", "you have entered wrong password !");
            modelAndView.setViewName("index");
            return modelAndView;
        }
        if (user1 != null) {
            modelAndView.setViewName("userhome");
        } else {
            modelAndView.addObject("msg", "You haven't registered yet !");
            modelAndView.setViewName("index");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }


    @RequestMapping(value = "/new/User/register", method = RequestMethod.POST)
    public ModelAndView getMessage(@ModelAttribute("user") User user) {
        Role role = new Role();
        role.setRole("admin");
        user.setRoles(new HashSet<Role>() {
            {
                add(role);
                userService.saveUser(user);
            }
        });
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello");
        return modelAndView;
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerPage(ModelAndView model) {
        model.setViewName("registration");
        model.addObject("user", new User());
        return model;
    }


}
