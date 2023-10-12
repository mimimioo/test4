package com.example.project4.controller;

import com.example.project4.dto.NotificationFormDto;
import com.example.project4.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;

import javax.validation.Valid;


@Controller
public class AdminController {

    @Autowired
    private BoardService boardService;
    /*게시글 폼 이동*/
    @GetMapping(value="/admin/notificationBoard/new")
        public String notificationForm(Model model) {

        model.addAttribute("board", "notice");
        return "board/new_board";
    }
    /*게시글 생성*/
    @PostMapping(value="/admin/notificationBoard/new")
    public String notificationNew(@Valid NotificationFormDto notificationFormDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "board/new_Board";
        }
        try {
            boardService.saveBoard(notificationFormDto);
            System.out.println(notificationFormDto.getTitle());
            System.out.println(notificationFormDto.getContent());
        } catch (Exception e){
            model.addAttribute("errorMessage", "게시글 등록 중 에러가 발생하였습니다.");
            return "Board/new_Board";
        }

        return "redirect:/";
    }

    /*게시글 조회*/
@GetMapping(value="/notificationBoard/{notificationId}")
    public String notificationDetail(@PathVariable("notificationId") Long notificationId, Model model) {
        try{
            NotificationFormDto notificationFormDto = boardService.notificationDetail(notificationId);
            model.addAttribute("notificationFormDto", notificationFormDto);
        } catch(EntityNotFoundException e) {
            model.addAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
            model.addAttribute("notificationFormDto", new NotificationFormDto());
            return "redirect:/";
        }

        return "board/board";
    }

    /*게시글 수정 폼*/
    @GetMapping(value="/admin/notificationBoard/{notificationId}")
    public String notificationUpdateDetail(@PathVariable("notificationId") Long notificationId, Model model) {
        try{
            NotificationFormDto notificationFormDto = boardService.notificationDetail(notificationId);
            model.addAttribute("notificationFormDto", notificationFormDto);
        } catch(EntityNotFoundException e) {
            model.addAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
            model.addAttribute("notificationFormDto", new NotificationFormDto());
            return "redirect:/";
        }

        return "board/new_board";
    }
    /*게시글 수정*/
    @PostMapping(value="/admin/notificationBoard/{boardId}")
    public String notificationUpdate(@Valid NotificationFormDto notificationFormDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "board/board";
        }
        try {
            boardService.updateBoard(notificationFormDto);
        } catch (Exception e){
            model.addAttribute("errorMessage", "게시글 등록 중 에러가 발생하였습니다.");
            return "Board/boardDetail";
        }

        return "redirect:/";
    }
    /*게시글 삭제*/
    @GetMapping(value="/admin/notificationBoard/delete/{notificationId}")
    public String notificationDelete(@PathVariable("notificationId") Long notificationId) {
        boardService.deleteBoard(notificationId);

        return "redirect:/";
    }
}
