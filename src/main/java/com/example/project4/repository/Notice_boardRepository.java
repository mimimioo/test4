package com.example.project4.repository;


import com.example.project4.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Notice_boardRepository extends JpaRepository<Notification, Long> {



}