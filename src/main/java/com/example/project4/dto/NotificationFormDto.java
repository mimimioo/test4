package com.example.project4.dto;

import com.example.project4.entity.Notification;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NotificationFormDto {
    private Long notificationId;

    private Long userId;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotNull(message = "내용을 입력해주세요.")
    private String content;

    private String createBy;

    private String reg_time;

    private Long view_count;

    private Long like_count;

    private boolean isLiked;

    /*엔티티와 dto 매핑을 지원해주는 매퍼 인스턴스 생성*/
    private static ModelMapper modelMapper = new ModelMapper();

    public Notification createNotification(){
        return modelMapper.map(this, Notification.class);
    }

    /*Notification엔티티와 NotificationFormDto를 매핑하여 dto를 리턴함.
    * 리턴된 dto는 모델에 담아 뷰에 뿌릴 수 있음*/
    public static NotificationFormDto of(Notification notification){
        return modelMapper.map(notification,NotificationFormDto.class);
    }
}
