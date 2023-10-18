package com.example.project4.entity;

import com.example.project4.dto.NotificationFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="notice_board")
@Getter
@Setter
@ToString
/* BaseEntity는 작성일에 대한 정보를 저장 */
public class Notification extends BaseEntity {
    @Id
    @Column(name="board_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notificationId;            //공지글 코드

    @Column(name="user_name")
    private String name;       //이름

    @Column(name="title")
    private String title;       //제목

    @Column(name="content")
    @Lob
    private String content;     //공지글 내용

    @Column(name="views")
    private Long view_count;    //조회수

    @Column(name="likes")
    private Long like_count;    //좋아요 수


    public void updateEntity(NotificationFormDto notificationFormDto) {
        this.title = notificationFormDto.getTitle();
        this.content = notificationFormDto.getContent();
    }
    public Long viewCount() {
        this.view_count+=1;

        return view_count;
    }
    public Long likeCount(Long data) {
        this.like_count+=data;

        return like_count;
    }
}
