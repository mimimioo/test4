package com.example.project4.repository;

import com.example.project4.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface notice_boardRepository extends JpaRepository<Notification, Long> {

}
