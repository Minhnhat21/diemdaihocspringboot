package com.example.thongbaotrungtuyendh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GreetingController {
    @RequestMapping("/login")
    public String helloWorld() {
        return "login.html";
    }

    @RequestMapping("/homepage")
    public String homePage() {
        return "homepage.html";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage() {
        return "adminpage.html";
    }

    @RequestMapping("/table")
    public String tablePage() {
        return "table-basic.html";
    }
}
