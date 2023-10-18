package com.example.project4.entity;

import com.example.project4.dto.NormalFormDto;
import com.example.project4.dto.NotificationFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="normal_board")
@Getter
@Setter
@ToString
/* BaseEntity는 작성일에 대한 정보를 저장 */
public class Normal extends BaseEntity {
    @Id
    @Column(name="board_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long normalId;            //공지글 코드

    @Column(name="title")
    private String title;       //제목

    @Column(name="content")
    @Lob
    private String content;     //공지글 내용

    @Column(name="views")
    private Long view_count;    //조회수

    @Column(name="likes")
    private Long like_count;    //좋아요 수


//    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
//    private List<NoticeLike> likes = new ArrayList<>();

    public void updateEntity(NormalFormDto normalFormDto) {
        this.title = normalFormDto.getTitle();
        this.content = normalFormDto.getContent();
    }
    // 게시물에 이미지 추가하는 메서드
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();
    public void addImage(Image image) {
        images.add(image);
        image.setBoard(this); // Image 엔티티의 board 외래 키 설정
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
