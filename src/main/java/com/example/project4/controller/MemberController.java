package com.example.project4.controller;

import com.example.project4.Exeption.SomeSpecificException;
import com.example.project4.entity.Member;
import com.example.project4.dto.MemberFormDto;
import com.example.project4.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "/mypage")
    public String myPage(Model model) {
        try {
            // 현재 사용자의 이메일 가져오기
            String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

            Member member = memberService.getMemberData(email);
            model.addAttribute("myPage", member);
            return "member/mypage"; // 정보 출력 폼
        } catch (EntityNotFoundException ex) {
            // 회원을 찾을 수 없는 경우에 대한 예외 처리해서 오류페이지로 넘기기
            model.addAttribute("error", "Member not found");
            return "errorPage"; // 오류 페이지 템플릿 이름
        }
    }

    @PostMapping(value = "/mypage")
    public String updateMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 에러가 있는 경우
            return "member/mypage";
        }

        try {
            Member member = Member.updateMember(memberFormDto, passwordEncoder, memberRepository);
            if (passwordEncoder.matches(memberFormDto.getUpdate_pw(), member.getPassword())) {
                // 비밀번호가 일치하면 정보 업데이트
                memberService.saveMember(member);
                model.addAttribute("successMessage", "회원 정보가 성공적으로 업데이트되었습니다.");
            } else {
                // 비밀번호가 일치하지 않으면 에러 메시지 설정
                model.addAttribute("passwordError", "비밀번호가 일치하지 않습니다.");
                return "member/mypage"; // 비밀번호가 일치하지 않을 때 마이페이지로 다시 이동
            }
        } catch (SomeSpecificException e) {
            // 수정이 실패한 경우, 해당 예외에 따라 에러 메시지를 추가하거나 다른 처리를 수행
            model.addAttribute("errorMessage", "수정 중 오류가 발생했습니다: " + e.getMessage());
        }

        return "member/mypage"; // 비밀번호가 일치하지 않을 때 마이페이지로 다시 이동
    }

//    @PostMapping(value="/mypage")
//    public String updateMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            // 유효성 검사 에러가 있는 경우
//            return "member/mypage";
//        }
//
//        try {
//            Member member = Member.updateMember(memberFormDto, passwordEncoder, memberRepository);
//            memberService.saveMember(member);
//            // 수정이 성공한 경우 성공 메시지를 추가
//            model.addAttribute("successMessage", "회원 정보가 성공적으로 업데이트되었습니다.");
//        } catch (SomeSpecificException e) {
//            // 수정이 실패한 경우, 해당 예외에 따라 에러 메시지를 추가하거나 다른 처리를 수행
//            model.addAttribute("errorMessage", "수정 중 오류가 발생했습니다: " + e.getMessage());
//        }
//
//        // 리디렉션 또는 포워딩, 여기서는 포워딩
//        return "forward:/";
//    }

//    @PostMapping(value="/mypage")
//    public String updateMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
//        if (bindingResult.hasErrors()){
//            return "member/mypage";
//        }
//        try{
//            Member member = Member.updateMember(memberFormDto, passwordEncoder, memberRepository);
//            memberService.saveMember(member);
//        }
//        catch (IllegalStateException e){
//            model.addAttribute("erroMessage", e.getMessage());
//            return "member/mypage";
//        }
//        return "redirect:/";
//    }

//    @PostMapping("/mypage")
//    public String updateMyPage(@ModelAttribute MyPageForm myPageForm, Model model) {
//        // 현재 사용자 정보를 가져오는 코드 (세션 또는 사용자 아이디를 기반으로)
//        Member currentUser = ... // 현재 사용자 정보를 가져오는 코드
//
//        // 입력한 비밀번호와 현재 사용자의 비밀번호 비교
//        if (passwordEncoder.matches(myPageForm.getUpdatePw(), currentUser.getPassword())) {
//            // 비밀번호가 일치하면 정보 업데이트
//            currentUser.setName(myPageForm.getName());
//            // 다른 정보도 업데이트
//            // ...
//
//            // 업데이트된 정보를 저장
//            memberService.updateMember(currentUser);
//            return "redirect:/members/mypage";
//        } else {
//            // 비밀번호가 일치하지 않으면 에러 메시지 설정
//            model.addAttribute("passwordError", "비밀번호가 일치하지 않습니다.");
//            return "members/mypage"; // 비밀번호가 일치하지 않을 때 마이페이지로 다시 이동
//        }
//    }

    @PostMapping(value="/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "member/createAccount";
        }
        // 이메일 중복 체크를 위한 로직 추가
        if (memberService.isEmailAlreadyInUse(memberFormDto.getEmail())) {
            model.addAttribute("errorMessage", "중복된 이메일 주소입니다.");
            return "member/createAccount";
        }
        try{
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        }
        catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/createAccount";
        }
        return "redirect:/";
    }

    @GetMapping(value ="/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/createAccount";

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





    //회원삭제
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteMember(Model model) {
        // 현재 사용자의 이메일 가져오기
        String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        try {
            // 회원 정보 및 관련 정보를 삭제
            memberService.deleteMember(email);

            // 로그아웃 처리 (선택 사항)
            SecurityContextHolder.clearContext();

            // 탈퇴 성공 메시지
            model.addAttribute("successMessage", "회원 탈퇴가 완료되었습니다.");
        } catch (EntityNotFoundException ex) {
            // 회원을 찾을 수 없는 경우에 대한 예외 처리
            model.addAttribute("errorMessage", "회원을 찾을 수 없습니다.");
        } catch (Exception e) {
            // 그 외의 오류에 대한 예외 처리
            model.addAttribute("errorMessage", "탈퇴 중 오류가 발생했습니다.");
        }

        return "forward:/logout"; // 로그아웃 페이지로 리다이렉트 (선택 사항)
    }

}