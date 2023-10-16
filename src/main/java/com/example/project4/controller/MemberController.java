package com.example.project4.controller;

import com.example.project4.entity.Member;
import com.example.project4.dto.MemberFormDto;
import com.example.project4.repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import com.example.project4.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

//    @GetMapping(value ="/mypage")
//    public String myPage(Model model){
//        model.addAttribute("memberFormDto", new MemberFormDto());
//        return "member/mypage";
//
//    }
//@GetMapping(value = "/mypage")
//public String myPage(Model model) {
//    // Replace "user@example.com" with the actual email you want to fetch
//    String email = "aaa@naver.com";
//
//    try {
//        Member member = memberService.getMemberData(email);
//        model.addAttribute("myPage", member);
//        return "member/myPage"; // The name of the template to display member data
//    } catch (EntityNotFoundException ex) {
//        // Handling the case when a member with the provided email is not found
//        model.addAttribute("error", "Member not found");
//        return "errorPage"; // The name of the error template
//    }
//}
@GetMapping(value = "/mypage")
public String myPage(Model model) {
    try {
        // 현재 사용자의 이메일 가져오기
        String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        Member member = memberService.getMemberData(email);
        model.addAttribute("myPage", member);
        return "member/myPage"; // 정보 출력 폼
    } catch (EntityNotFoundException ex) {
        // 회원을 찾을 수 없는 경우에 대한 예외 처리해서 오류페이지로 넘기기
        model.addAttribute("error", "Member not found");
        return "errorPage"; // 오류 페이지 템플릿 이름
    }
}

    @PostMapping(value="/mypage")
    public String updateMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "member/mypage";
        }
        try{
            Member member = Member.updateMember(memberFormDto, passwordEncoder, memberRepository);
            memberService.saveMember(member);
        }
        catch (IllegalStateException e){
            model.addAttribute("erroMessage", e.getMessage());
            return "member/mypage";
        }
        return "redirect:/";
    }


    @GetMapping(value ="/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/createAccount";

    }
    @PostMapping(value="/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "member/createAccount";
        }
        try{
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        }
        catch (IllegalStateException e){
            model.addAttribute("erroMessage", e.getMessage());
            return "member/createAccount";
        }
        return "redirect:/";
    }
    @GetMapping(value="/new/error")
    public  String createError(Model model){
        model.addAttribute("createErrorMsg", "비밀번호를 맞게 입력해주세요");
        return "member/createAccount";
    }

    @GetMapping(value = "/login")
    public String loginMember() {return  "member/login";}


    @GetMapping(value = "/login/error")
    public String loginError(Model model){
     model.addAttribute("loginErrorMsg", "아이디 혹은 비밀번호를 맞게 입력해주세요");
     return "member/login";
    }


    @ResponseBody
    @PostMapping("/nameElement")
    public String profile(@RequestBody String email) {
        System.out.println("들어온 이메일"+email);
        MemberFormDto member = memberService.readMember(email);
        String name = member.getName();
        String email2 = member.getEmail();
        System.out.println("디비에서 가져온 이름"+name);
        System.out.println("디비에서 가져온 메일"+email2);
        return name;
    }



}
