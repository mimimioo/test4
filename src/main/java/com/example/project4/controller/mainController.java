package com.example.project4.controller;

import com.example.project4.dto.NotificationFormDto;
import com.example.project4.service.BoardService;
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
    @Autowired
    BoardService boardService;
    @GetMapping(value = "/")
    public String gotoMain(Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 15);
        Page<NotificationFormDto> notices = mainService.getMainNoticePage(pageable);

        model.addAttribute("notices", notices);

        return "main";
    }


    /* 좋아요 클릭시 */
    @PostMapping(value="/notificationBoard/{notificationId}/like")
    @ResponseBody
    public String like(@RequestBody Map<String, Long> requestBody, @PathVariable("notificationId") Long notificationId) {
        /* 미완성, 현재 좋아요 클릭시 게시글 좋아요 카운트 기능만 완성함. */
        /* 멤버 기능 완성되면, 로그인한 유저가 해당 게시글 좋아요 했는지 여부 Boolean으로 반환하기 */
        /* 반환 후 boolean 값에 따라 제이 쿼리 사용하여 좋아요 아이콘 하트 또는 빈 하트로 나오게 수정. */

        System.out.println("실행됨");

        Long data = requestBody.get("data");
        System.out.println("-----------------------------------------------------");
        System.out.println(data);
        System.out.println(notificationId);
        System.out.println("------------------------------------------------------");

        Long like = boardService.like_count(data, notificationId);
        return like.toString();
    }
}
