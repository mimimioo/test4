package com.example.project4.dto;

import com.example.project4.entity.Reply;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
public class ReplyFormDto {
    private String email;
    @NotBlank(message = "댓글 제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String content;
    private String createBy;
    private String reg_time;

    public String getFormattedRegTime() {
        if (this.reg_time != null) {
            try {
                LocalDateTime regTime = LocalDateTime.parse(this.reg_time, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));
                return regTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            } catch (DateTimeParseException e) {
                // 유효하지 않은 형식의 경우 처리할 내용 추가
            }
        }
        return "";
    }

    /* 엔티티와 DTO 간의 매핑을 지원하는 ModelMapper 인스턴스 생성 */
    private static ModelMapper modelMapper = new ModelMapper();

    public Reply createReply(){
        return modelMapper.map(this, Reply.class);
    }

    /*Notification엔티티와 NotificationFormDto를 매핑하여 dto를 리턴함.
     * 리턴된 dto는 모델에 담아 뷰에 뿌릴 수 있음*/
    public static ReplyFormDto of(Reply reply){
        return modelMapper.map(reply,ReplyFormDto.class);
    }
}
