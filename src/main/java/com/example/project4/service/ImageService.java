package com.example.project4.service;


import com.example.project4.entity.Image;

import com.example.project4.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional

public class ImageService {
@Autowired
private FileService fileService;
@Autowired
private ImageRepository imageRepository;
@Value("${boardImgLocation}")
private String boardImgLocation;
    public void saveImage(MultipartFile imageFile) throws Exception{
        Image image = new Image();
        String oriImgName = imageFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            //로컬 이미지 이름
            imgName = fileService.uploadFile(boardImgLocation, oriImgName,
                    imageFile.getBytes());
            imgUrl = "/images/board/" + imgName;

            image.updateImg(oriImgName, imgName, imgUrl);
            imageRepository.save(image);

        }
    }
    private String boardLocation;

    public byte[] loadImage(String imageName) throws IOException {
        Path imagePath = Paths.get(boardImgLocation, imageName);
        return Files.readAllBytes(imagePath);
    }
}
