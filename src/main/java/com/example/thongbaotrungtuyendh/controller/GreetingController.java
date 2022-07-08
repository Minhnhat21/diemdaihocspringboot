package com.example.thongbaotrungtuyendh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/admin")
    public String adminPage() {
        return "adminpage.html";
    }

    @RequestMapping("/table")
    public String tablePage() {
        return "table-basic.html";
    }
}
