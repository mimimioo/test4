package com.example.project4.entity;

import com.example.project4.dto.NotificationFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="notification")
@Getter
@Setter
@ToString
/* BaseEntity는 작성일에 대한 정보를 저장 */
public class Notification extends BaseEntity {
    @Id
    @Column(name="notification_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notificationId;            //공지글 코드

    @Column(name="title")
    private String title;       //제목

    @Column(name="content")
    @Lob
    private String content;     //공지글 내용

    @Column(name="view_count")
    private Long view_count;    //조회수

    @Column(name="like_count")
    private Long like_count;    //좋아요 수

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="user_id")
//    private Member userId;

    public void updateEntity(NotificationFormDto notificationFormDto) {
        this.title = notificationFormDto.getTitle();
        this.content = notificationFormDto.getContent();
    }
    public Long viewCount() {
        this.view_count++;

        return view_count;
    }
    public Long likeCount(Long like) {
        this.like_count+=like;

        return like_count;
    }
}
