package com.example.project4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BoardController {
@GetMapping("/1")
    public String boardWriteForm1 () {
        return "member/mypage";
    }
    @GetMapping("/2")
    public String boardWriteFor2 () {
        return "member/login";
    }
    @GetMapping("/3")
    public String boardWriteForm3 () {
        return "member/createAccount";
    }
    @GetMapping("/4")
    public String boardWriteForm4 () {
        return "notice_board";
    }
    @GetMapping("/5")
    public String boardWriteForm5 () {
        return "notice_new_board";
    }
}

