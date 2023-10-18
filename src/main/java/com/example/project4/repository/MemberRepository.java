package com.example.project4.repository;

import com.example.project4.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.project4.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

   Optional <Member> findByEmail(String email);
   boolean existsByEmail(String email);

}
