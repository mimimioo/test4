package com.example.project4.repository;

import com.example.project4.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
//    List<Reply> findByBoardId(Long boardId);

    // Notice_boardRepository와 ReplyRepository의 메소드 모두 사용 가능

}