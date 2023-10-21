package com.example.project4.controller;

import com.example.project4.dto.NotificationFormDto;
import com.example.project4.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
public class mainController {
    @Autowired
    MainService mainService;
    @GetMapping(value = "/")
    public String gotoMain(Model model) {
        /* Sort는 정렬 정보를 저장하는 객체임 */
        /* 정렬 조건이 한개라면 Sort.by(Sort.Direction.ASC, "like_count") */
        /* 정렬 조건이 2개 이상일 때는 아래와 같이 Sort의 Order라는 객체에 설정이 가능함 */
        /* 아래는 인기글 정렬 조건을 설정함 */
        Sort popularPost_sort = Sort.by(
                Sort.Order.desc("like_count"),
                Sort.Order.desc("regTime")
        );
        Sort board_sort = Sort.by(
                Sort.Order.desc("regTime")
        );
        /* 페이지네이션 설명 : 5개 데이터로 이루어진 0 페이지를 최신순으로 불러옴/ 인기글 조회하기 */
        Pageable popularPost_pageable = PageRequest.of(0, 5, popularPost_sort);
        Page<NotificationFormDto> popular_post = mainService.getPopularBoard(popularPost_pageable);
        /* 페이지네이션 설명 : 15개 데이터로 이루어진 0 페이지를 최신순으로 불러옴/ 일반 게시글 조회하기 */
        Pageable board_pageable = PageRequest.of(0, 15, board_sort);
        Page<NotificationFormDto> boards = mainService.getMainNoticePage(board_pageable);
        /* 페이지네이션 설명 : 5개 데이터로 이루어진 0 페이지를 최신순으로 불러옴/ 공지 조회하기 */
        Pageable notice_pageable = PageRequest.of(0, 5, board_sort);
        Page<NotificationFormDto> notices = mainService.getMainNoticePage(notice_pageable);

        model.addAttribute("popular_post", popular_post);
        model.addAttribute("boards", boards);
        model.addAttribute("notices", notices);

        return "main";
    }


}
