package com.example.project4.controller;

import com.example.project4.dto.NotificationFormDto;
import com.example.project4.entity.Member;
import com.example.project4.dto.MemberFormDto;
import com.example.project4.entity.Notification;
import com.example.project4.repository.MemberRepository;
import com.example.project4.service.BoardService;
import org.springframework.http.ResponseEntity;
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
import java.util.List;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final BoardService boardService;

    @GetMapping(value = "/mypage")
    public String myPage(Model model) {

        try {
            // 현재 사용자의 이메일 가져오기
            String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            Member member = memberService.getMemberData(email);

            List<NotificationFormDto> notificationFormDtos = boardService.notificationListLoad(email);
//            뷰에 데이터 뿌리기
            model.addAttribute("myPage", member);
            model.addAttribute("notificationFormDtos", notificationFormDtos);
            return "member/mypage"; // 정보 출력 폼
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

    //아이디 중복체크
    @RequestMapping(value = "/checkEmailDuplicate", method = RequestMethod.POST)
    public String checkEmailDuplicate(@RequestBody MemberFormDto.CheckEmailRequest request) {
        String email = request.getEmail();
        if (memberService.isEmailDuplicate(email)) {
            return "DUPLICATE";
        }
        return "NOT_DUPLICATE";
    }
    @GetMapping("/checkEmailDuplicate")
    public ResponseEntity<String> checkEmailDuplicate() {
        // GET 요청 처리 로직을 여기에 추가
        return ResponseEntity.ok("GET 요청이 처리되었습니다.");
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