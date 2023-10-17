package com.example.project4.dto;

import antlr.collections.impl.BitSet;
import com.example.project4.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class MemberFormDto {
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;


//    @NotNull(message = "우편번호는 필수 입력 값입니다")
    private int zipCode;
//    @NotEmpty(message = "상세 주소는 필수 입력 값입니다.")
    private String detail_Address;
//    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String address;


    /*엔티티와 dto 매핑을 지원해주는 매퍼 인스턴스 생성*/
    private static ModelMapper modelMapper = new ModelMapper();


    public Member createMember(){
        return modelMapper.map(this, Member.class);
    }

    /*Notification엔티티와 NotificationFormDto를 매핑하여 dto를 리턴함.
     * 리턴된 dto는 모델에 담아 뷰에 뿌릴 수 있음*/
    public static MemberFormDto of(Member member){
        return modelMapper.map(member,MemberFormDto.class);
    }

    public class CheckEmailRequest {
        private String email;
        public String getEmail() {
            return email;
        }
        // Getter and setter methods
    }
}

