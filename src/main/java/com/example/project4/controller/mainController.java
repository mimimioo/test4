package com.example.project4.controller;

import com.example.project4.entity.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainController {
    @GetMapping(value = "/")
    public String gotoMain() {

        return "main";
    }
}
