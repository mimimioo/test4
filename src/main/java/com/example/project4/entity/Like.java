package com.example.project4.entity;

import lombok.Getter;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @Getter
    @Column(name = "liked")
    private boolean liked;

}
