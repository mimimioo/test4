package com.example.project4.service;

import com.example.project4.dto.ReplyFormDto;
import com.example.project4.entity.Reply;
import com.example.project4.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    @Autowired
    private ReplyRepository replyRepository;

    // ReplyService 관련 메서드들 추가
    @Autowired
    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

//    public List<Reply> getRepliesByBoardId(Long boardId) {
////        return replyRepository.findByBoardId(boardId);
//        return null;
//    }

    public ReplyFormDto saveReply(ReplyFormDto replyFormDto) {
        Reply reply=replyFormDto.createReply();
        return ReplyFormDto.of(replyRepository.save(reply));
    }

    public void deleteReply(Long replyId) {
        replyRepository.deleteById(replyId);
    }
}