package com.example.project4.dto;

import com.example.project4.entity.Normal;
import com.example.project4.entity.Notification;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
public class NormalFormDto {
    private Long normalId;

    private Long userId;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotNull(message = "내용을 입력해주세요.")
    private String content;

    private String createBy;

    private String reg_time;

    private Long view_count;

    private Long like_count;

    private String image_Address;

    private boolean isLiked;

    /*엔티티와 dto 매핑을 지원해주는 매퍼 인스턴스 생성*/
    private static ModelMapper modelMapper = new ModelMapper();

    public Normal createNormal(){
        return modelMapper.map(this, Normal.class);
    }

    /*Notification엔티티와 NotificationFormDto를 매핑하여 dto를 리턴함.
     * 리턴된 dto는 모델에 담아 뷰에 뿌릴 수 있음*/
    public static NormalFormDto of(Normal normal){
        return modelMapper.map(normal,NormalFormDto.class);
    }
    public String getFormattedRegTime() {
        if (this.reg_time != null) {
            try {
                // reg_time 필드를 LocalDateTime으로 변환
                LocalDateTime regTime = LocalDateTime.parse(this.reg_time, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));
                // LocalDateTime을 원하는 형식으로 변환
                String formattedDate = regTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                return formattedDate;
            } catch (DateTimeParseException e) {
                System.out.println("------------------------------------------");
                System.out.println(e);
                System.out.println("------------------------------------------");
                // 유효하지 않은 형식의 경우 처리할 내용 추가
            }
        }
        return ""; // 또는 다른 기본값을 반환할 수 있습니다.
    }
}
