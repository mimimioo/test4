package com.example.project4.repository;

import com.example.project4.entity.Like;
import com.example.project4.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeInfoRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l.liked FROM Like l WHERE l.member = :memberId AND l.notification = :boardId")
    boolean findByMemberIdAndBoardId(@Param("memberId") Long member_id, @Param("boardId") Long board_id);
}
