package com.example.project4.controller;

import com.example.project4.dto.NotificationFormDto;
import com.example.project4.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
public class mainController {
    @Autowired
    MainService mainService;
    @GetMapping(value = "/")
    public String gotoMain(Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 15);
        Page<NotificationFormDto> notices = mainService.getMainNoticePage(pageable);
        System.out.println("실행됨");
        model.addAttribute("notices", notices);

        return "main";
    }

}
