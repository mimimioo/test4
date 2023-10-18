package com.example.project4.dto;


import com.example.project4.entity.Normal;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.awt.*;

@Getter
@Setter
public class ImageFormDto {
    private long number;

    @NotNull
    private Normal board; // Normal 엔티티의 관계를 표현

    @NotNull
    @NotBlank
    private String imageName;

    @Getter
    @NotNull
    @NotBlank
    private String image_Address;


    private String originalImageName;

    /*엔티티와 dto 매핑을 지원해주는 매퍼 인스턴스 생성*/
    private static ModelMapper modelMapper = new ModelMapper();

    public Image createImage() {
        return modelMapper.map(this, Image.class);
    }

    public static ImageFormDto of(Image image) {
        return modelMapper.map(image, ImageFormDto.class);
    }
}