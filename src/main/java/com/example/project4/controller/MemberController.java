package com.example.project4.controller;

import com.example.project4.entity.Member;
import com.example.project4.dto.MemberFormDto;
import org.springframework.ui.Model;
import com.example.project4.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;


    @GetMapping(value ="/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";

    }
    @PostMapping(value="/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "member/memberForm";
        }
        try{
            Member member = Member.createMember(memberFormDto, passwordEncoder);
        }
        catch (IllegalStateException e){
            model.addAttribute("erroMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember() {return  "member/memberLoginForm";}


    @GetMapping(value = "/login/error")
    public String loginError(Model model){
     model.addAttribute("loginErrorMsg", "아이디 혹은 비밀번호를 맞게 입력해주세요");
     return "/member/memberLoginForm";
    }


}
