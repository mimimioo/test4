package com.example.project4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainController {
    @GetMapping(value = "/main")
    public String gotoMain() {
        return "main";
    }
}
