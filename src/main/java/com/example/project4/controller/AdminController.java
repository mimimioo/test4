package com.example.project4.controller;

import com.example.project4.dto.NotificationFormDto;
import com.example.project4.entity.Member;
import com.example.project4.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import javax.validation.Valid;
import java.util.Map;


@Controller
public class AdminController {

    @Autowired
    private BoardService boardService;
    /*게시글 작성 폼 이동*/
    @GetMapping(value="/admin/notificationBoard/new")
        public String notificationForm(Model model) {
        model.addAttribute("notificationFormDto", new NotificationFormDto());
        model.addAttribute("board", "notice");
        return "board/notice_new_board";
    }
    /*게시글 생성*/
    @PostMapping(value="/admin/notificationBoard/new")
    public String notificationNew(@Valid NotificationFormDto notificationFormDto, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            return "board/notice_new_board";
        }
        try {
            boardService.saveBoard(notificationFormDto);
        } catch (Exception e){
            model.addAttribute("errorMessage", "게시글 등록 중 에러가 발생하였습니다.");

            return "board/notice_new_board";
        }

        return "redirect:/";
    }

    /*게시글 조회*/
@GetMapping(value="/notificationBoard/{notificationId}")
    public String notificationDetail(@PathVariable("notificationId") Long notificationId, Model model) {
        try{
            NotificationFormDto notificationFormDto = boardService.notificationDetail(notificationId);
            /* 게시글 조회 시*/
            /* 방문자의 경우 좋아요 표시 빈하트 */
            /* 유저의 경우 좋아요 표시는 DB에 저장된 데이터에 따라 반환하기 */
            /* 방문자와 유저는 시큐리티로 분리하기 */
            model.addAttribute("notificationFormDto", notificationFormDto);
        } catch(EntityNotFoundException e) {
            model.addAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
            model.addAttribute("notificationFormDto", new NotificationFormDto());
            return "redirect:/";
        }

        return "board/notice_board";
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

        return "redirect:/";
    }
    /*게시글 삭제*/
    @GetMapping(value="/admin/notificationBoard/delete/{notificationId}")
    public String notificationDelete(@PathVariable("notificationId") Long notificationId) {
        boardService.deleteBoard(notificationId);

        return "redirect:/";
    }
    /* 좋아요 클릭시 */
    @PostMapping(value="/notificationBoard/{notificationId}/like")
    @ResponseBody
    public String like(@RequestBody Map<String, Long> requestBody, @PathVariable("notificationId") Long notificationId) {
        /* 미완성, 현재 좋아요 클릭시 게시글 좋아요 카운트 기능만 완성함. */
        /* 멤버 기능 완성되면, 로그인한 유저가 해당 게시글 좋아요 했는지 여부 Boolean으로 반환하기 */
        /* 반환 후 boolean 값에 따라 제이 쿼리 사용하여 좋아요 아이콘 하트 또는 빈 하트로 나오게 수정. */

        Long data = requestBody.get("data");

        Long like = boardService.like_count(data, notificationId);
        return like.toString();
    }
}
