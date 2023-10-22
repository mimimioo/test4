package com.example.project4.controller;

import com.example.project4.dto.NotificationFormDto;
import com.example.project4.dto.ReplyFormDto;
import com.example.project4.entity.Reply;
import com.example.project4.service.BoardService;
import com.example.project4.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Controller
public class AdminController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ReplyService replyService;

    /*게시글 작성 폼 이동*/
    @GetMapping(value="/admin/notificationBoard/new")
    public String notificationForm(Model model) {
        model.addAttribute("notificationFormDto", new NotificationFormDto());
        model.addAttribute("board", "notice");
        return "board/notice_new_board";
    }
    /*게시글 생성*/
    @PostMapping(value="/admin/notificationBoard/new")
    public String notificationNew(@Valid NotificationFormDto notificationFormDto, BindingResult bindingResult, Model model, Principal principal) {
        if(bindingResult.hasErrors()){
            return "board/notice_new_board";
        }
        try {
            notificationFormDto.setEmail(principal.getName());
            boardService.saveBoard(notificationFormDto);
        } catch (Exception e){
            model.addAttribute("errorMessage", "게시글 등록 중 에러가 발생하였습니다.");
            System.out.println(e);
            return "board/notice_new_board";
        }

        return "redirect:/";
    }

    /*게시글 조회*/
    @GetMapping(value="/notificationBoard/{notificationId}")
    public String notificationDetail(@PathVariable("notificationId") Long notificationId, Model model, Principal principal) {
        try {
            /* 게시글 조회 시*/
            /* 방문자의 경우 좋아요 표시 빈하트 */
            /* 유저의 경우 좋아요 표시는 DB에 저장된 데이터에 따라 반환하기 */
            /* 방문자와 유저는 시큐리티로 분리하기 */
            NotificationFormDto notificationFormDto;
            if(principal!=null) {
                notificationFormDto = boardService.like_count_load(notificationId, principal.getName());
            } else {
                notificationFormDto = boardService.notificationDetail(notificationId);
                notificationFormDto.setLiked(false);
            }
//              List<Reply> replies = replyService.getRepliesByBoardId(notificationId);

//              model.addAttribute("replies", replies);
                model.addAttribute("notificationFormDto", notificationFormDto);
                model.addAttribute("replyFormDto", new ReplyFormDto()); // 추가: 댓글 입력 폼을 초기화
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
            model.addAttribute("notificationFormDto", new NotificationFormDto());
            return "redirect:/";
        }
        return "board/notice_board";
    }

    @PostMapping(value = "/notificationBoard/{notificationId}/addReply")
    public String addReply(
            @PathVariable("notificationId") Long notificationId,
            @RequestBody ReplyFormDto replyFormDto,
            BindingResult bindingResult,
            Principal principal,
            Model model) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류 처리 (예: 필수 필드 누락 등)
            // 오류 처리 코드를 추가하세요.
            return "redirect:/notificationBoard/" + notificationId;
        }
        replyFormDto.setEmail(principal.getName());

        // Reply 저장
        replyService.saveReply(replyFormDto);
        //해야할 것 : 게시글 아이디에 따른 댓글 조회시 반환되는 리스트를 DTO에 담기
        model.addAttribute("replyFormDto", replyFormDto);

        return "redirect:/notificationBoard/" + notificationId;
    }

    /*게시글 수정 폼*/
    @GetMapping(value="/admin/notificationBoard/{notificationId}")
    public String notificationUpdateDetail(@PathVariable("notificationId") Long notificationId, Model model) {
        try{
            NotificationFormDto notificationFormDto = boardService.notificationDetail(notificationId);
            model.addAttribute("notificationFormDto", notificationFormDto);
            model.addAttribute("board", "update_notice");
        } catch(EntityNotFoundException e) {
            model.addAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
            model.addAttribute("notificationFormDto", new NotificationFormDto());
            return "redirect:/";
        }

        return "board/notice_new_board";
    }
    /*게시글 수정*/
    @PostMapping(value="/admin/notificationBoard/{notificationId}")
    public String notificationUpdate(@PathVariable("notificationId") Long notificationId, @Valid NotificationFormDto notificationFormDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "board/notice_board";
        }
        try {
//            notificationFormDto.setNotificationId(notificationId);
            boardService.updateBoard(notificationFormDto);
        } catch (Exception e){
            model.addAttribute("errorMessage", "게시글 등록 중 에러가 발생하였습니다.");
            return "board/notice_board";
        }

        return "redirect:/members/mypage";
    }
    /*게시글 삭제*/
    @GetMapping(value="/admin/notificationBoard/delete/{notificationId}")
    public String notificationDelete(@PathVariable("notificationId") Long notificationId) {
        boardService.deleteBoard(notificationId);

        return "redirect:/members/mypage";
    }

    @PostMapping(value="/notificationBoard/{notificationId}/like")
    @ResponseBody
    public NotificationFormDto likeInfo(@RequestBody Long data, @PathVariable("notificationId") Long notificationId, Model model, Principal principal) {
        try{
            NotificationFormDto notificationFormDto = boardService.notificationDetail(notificationId);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication.isAuthenticated()) {
                notificationFormDto = boardService.like_count(data, notificationId, principal.getName());
                return notificationFormDto;
            }
            /* 게시글 조회 시*/
            /* 방문자의 경우 좋아요 표시 빈하트 */
            /* 유저의 경우 좋아요 표시는 DB에 저장된 데이터에 따라 반환하기 */
            /* 방문자와 유저는 시큐리티로 분리하기 */
        } catch(EntityNotFoundException e) {
            model.addAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
            model.addAttribute("notificationFormDto", new NotificationFormDto());
        }
        return new NotificationFormDto();
    }
}