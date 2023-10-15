package com.example.project4.entity;

import javax.persistence.*;

@Entity
@Table(name = "like_table")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @Column(name = "liked")
    private boolean liked;

    public boolean isLiked() {
        if(this.liked) {
            return true;
        } else
            return false;
    }
}