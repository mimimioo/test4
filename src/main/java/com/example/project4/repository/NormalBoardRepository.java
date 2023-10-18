
package com.example.project4.repository;


import com.example.project4.entity.Normal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalBoardRepository extends JpaRepository<Normal, Long> {
}