
package com.example.project4.entity;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Table(name = "image")
@ToString
@Data
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "number")
    private Long number;

//외래키
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Normal board;

//이미지 이름
    @Column(name = "image_name", unique = true, nullable = false)
    private String imageName;
//이미지 주소
    @Column(name = "image_address", unique = true, nullable = false)
    private String imageAddress;
//오리지널 이미지 이름
    @Column(name = "original_image_name")
    private String originalImageName;

    public void setTitle(String title) {
    }

    public void setContent(String content) {
    }
    public void updateImg(String originalImageName, String imageName, String imageAddress){
        this.originalImageName = originalImageName;
        this.imageName = imageName;
        this.imageAddress = imageAddress;
    }
}



