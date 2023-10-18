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
            NotificationFormDto notificationFormDto = boardService.notificationDetail(notificationId);
            System.out.println("notificationId : " + notificationId);
            System.out.println("notificationFormDto getTitle : " + notificationFormDto.getTitle());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("authentication 전 : " );
            if (authentication.isAuthenticated()) {
                System.out.println("authentication true  일경우 : " );
                notificationFormDto = boardService.like_count_load(notificationId, principal.getName());
                System.out.println("notificationFormDto getLike_count : " + notificationFormDto.getLike_count());
                System.out.println("authentication true  일경우 , 조회 후. : " );
            }
            System.out.println("모델 속성 notificationFormDto 실행전 " );
            model.addAttribute("notificationFormDto", notificationFormDto);
            System.out.println("모델 속성 notificationFormDto  실행후 " );

//            List<Reply> replies = replyService.getRepliesByBoardId(notificationId);
//            model.addAttribute("replies", replies);
            System.out.println("모델 속성 replyFormDto 실행전 " );
            model.addAttribute("replyFormDto", new ReplyFormDto()); // 추가: 댓글 입력 폼을 초기화
            System.out.println("모델 속성 replyFormDto 실행후 " );
        } catch (EntityNotFoundException e) {
            System.out.println("오류 발생 후  " );
            model.addAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
            model.addAttribute("notificationFormDto", new NotificationFormDto());
            return "redirect:/";
        }
        System.out.println("정상 동작 후 뷰 가기 전  " );
        return "board/notice_board";
    }

    @PostMapping(value = "/notificationBoard/{notificationId}/addReply")
    public String addReply(
            @PathVariable("notificationId") Long notificationId,
            @ModelAttribute("replyFormDto") ReplyFormDto replyFormDto,
            BindingResult bindingResult,
            Principal principal,
            Model model) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류 처리 (예: 필수 필드 누락 등)
            // 오류 처리 코드를 추가하세요.
            return "redirect:/notificationBoard/" + notificationId;
        }
        System.out.println("==============================================");
        System.out.println(replyFormDto.getTitle());
        System.out.println(replyFormDto.getContent());
        replyFormDto.setEmail(principal.getName());
        System.out.println(replyFormDto.getEmail());

        // Reply 저장
        replyFormDto=replyService.saveReply(replyFormDto);
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

        return "redirect:/";
    }
    /*게시글 삭제*/
    @GetMapping(value="/admin/notificationBoard/delete/{notificationId}")
    public String notificationDelete(@PathVariable("notificationId") Long notificationId) {
        boardService.deleteBoard(notificationId);

        return "redirect:/";
    }

    @PostMapping(value="/notificationBoard/{notificationId}/like")
    @ResponseBody
    public NotificationFormDto likeInfo(@RequestBody Long data, @PathVariable("notificationId") Long notificationId, Model model, Principal principal) {
        System.out.println("요청 도착함");
        try{
            NotificationFormDto notificationFormDto = boardService.notificationDetail(notificationId);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication.isAuthenticated()) {
                notificationFormDto = boardService.like_count(data, notificationId, principal.getName());
                System.out.println("요청 성공 결과 --------");
//                System.out.println(notificationFormDto.isLike());
                System.out.println(notificationFormDto.getLike_count());
                System.out.println("요청 성공 결과 --------");
                return notificationFormDto;
            }
            /* 게시글 조회 시*/
            /* 방문자의 경우 좋아요 표시 빈하트 */
            /* 유저의 경우 좋아요 표시는 DB에 저장된 데이터에 따라 반환하기 */
            /* 방문자와 유저는 시큐리티로 분리하기 */
        } catch(EntityNotFoundException e) {
            model.addAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
            model.addAttribute("notificationFormDto", new NotificationFormDto());
            System.out.println("트라이 캐치 오류 발생");
        }
        return new NotificationFormDto();
    }
}