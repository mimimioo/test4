package com.example.project4.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String contents;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "notification_id")
//    private Notification notification;

    // 다른 필드, 생성자, 게터/세터, 메서드 등 추가

//    public Reply(String title, String contents, Notification notification) {
//        this.title = title;
//        this.contents = contents;
//        this.notification = notification;
//    }

//    public Reply() {
//
//    }

    // 기타 필요한 메서드와 생성자 추가
}
