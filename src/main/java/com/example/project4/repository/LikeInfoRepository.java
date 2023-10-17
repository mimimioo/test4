package com.example.project4.repository;

import com.example.project4.entity.Like;
import com.example.project4.entity.Member;
import com.example.project4.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeInfoRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM Like l WHERE l.member = :member AND l.notification = :notification")
    Optional<Like> findMemberAndNotification(@Param("member") Member member, @Param("notification") Notification notification);

}
