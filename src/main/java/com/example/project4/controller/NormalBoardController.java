package com.example.project4.controller;

import com.example.project4.dto.NormalFormDto;

import com.example.project4.service.ImageService;
import com.example.project4.service.NormalBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;


@Controller
public class NormalBoardController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private NormalBoardService normalBoardService;
    @Value("${boardImgLocation}")
    private String boardImgLocation;

    /* 게시글 작성 뷰 반환 */
    @GetMapping("/board/write")
    public String boardWriteForm(Model model) {
        model.addAttribute("normalFormDto", new NormalFormDto());
        model.addAttribute("board", "normal");
        return "/board/normal_new_board";
    }

    /* 게시글 양식 제출 시 처리하는 컨트롤러 */
    @PostMapping("/board/write")
    public String boardWritePro(@Valid NormalFormDto normalFormDto, @RequestParam("image") MultipartFile imageFile, BindingResult bindingResult, Model model) {
        // 게시물 엔티티에 데이터를 저장할 수 있습니다.
        normalBoardService.saveBoard(normalFormDto);
        try {
            imageService.saveImage(imageFile);
        } catch (Exception e) {
            System.out.println(e);
            return "/board/normal_new_board";
        }

        // 작업이 완료되면 게시물 목록 페이지로 리다이렉트합니다.
        return "redirect:/";
    }

    @GetMapping("/board/{normalId}")
    public String boardDetail(@PathVariable Long normalId, Model model) {
        try {
            NormalFormDto normalFormDto = normalBoardService.boardDetail(normalId);
            normalFormDto.setImage_Address("/images/board/1922009f-c432-4da2-b964-1258a8074723.jpg");
            /* 게시글 조회 시*/
            /* 방문자의 경우 좋아요 표시 빈하트 */
            /* 유저의 경우 좋아요 표시는 DB에 저장된 데이터에 따라 반환하기 */
            /* 방문자와 유저는 시큐리티로 분리하기 */
            model.addAttribute("normalFormDto", normalFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
            model.addAttribute("normalFormDto", new NormalFormDto());
            return "redirect:/";
        }
        return "board/normal_board";
    }

    //게시글 업데이트
    @GetMapping("/board/update/{normalId}")
    public String boardUpdateForm(@PathVariable Long normalId, Model model) {
        try {
            NormalFormDto normalFormDto = normalBoardService.boardDetail(normalId);
            model.addAttribute("board", "update");
            model.addAttribute("normalFormDto", normalFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
            return "redirect:/";
        }
        return "/board/normal_new_board"; // 게시물 업데이트 폼
    }

    //업데이트
    @PostMapping("/board/update/{normalId}")
    public String boardUpdatePro(@PathVariable Long normalId, @Valid NormalFormDto normalFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "normal_new_board"; // 유효성 검사 실패 시 업데이트 폼 페이지로 다시 이동
        }
        normalFormDto = normalBoardService.updateBoard(normalFormDto); // 게시물 업데이트
        model.addAttribute("normalFormDto", normalFormDto);
        return "redirect:/"; // 업데이트된 게시물 상세 페이지로 리다이렉트
    }

    //삭제
    @GetMapping(value = "/board/delete/{normalId}")
    public String normalDelete(@PathVariable("normalId") Long normalId) {
        normalBoardService.deleteBoard(normalId);

        return "redirect:/";
    }
 /*   @PostMapping(value="/board/like/{normalId}")
    @ResponseBody
    public String like(@RequestBody Map<String, Long> requestBody, @PathVariable("normalId") Long normalId) {
        *//* 미완성, 현재 좋아요 클릭시 게시글 좋아요 카운트 기능만 완성함. *//*
     *//* 멤버 기능 완성되면, 로그인한 유저가 해당 게시글 좋아요 했는지 여부 Boolean으로 반환하기 *//*
     *//* 반환 후 boolean 값에 따라 제이 쿼리 사용하여 좋아요 아이콘 하트 또는 빈 하트로 나오게 수정. *//*

        Long data = requestBody.get("data");

        Long like = NormalBoardService.like_count(data, normalId);
        return like.toString();
    }*/
/*@Autowired
private ImageService imageService;

    @GetMapping("/board/image")
    public String boardImage(){
        return "/board/normal_board";
    }
    @PostMapping("/board/image")
    public String boardImageForm(){

        return "/board/normal_board";
    }*/


}

