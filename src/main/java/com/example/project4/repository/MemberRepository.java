package com.example.project4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.project4.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
}
