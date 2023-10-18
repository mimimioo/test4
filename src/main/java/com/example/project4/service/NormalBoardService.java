package com.example.project4.service;

import com.example.project4.dto.NormalFormDto;
import com.example.project4.dto.NotificationFormDto;
import com.example.project4.entity.Normal;
import com.example.project4.entity.Notification;
import com.example.project4.repository.NormalBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class NormalBoardService {

    private final NormalBoardRepository normalBoardRepository;

    public void saveBoard(NormalFormDto normalFormDto) {
        try {
            Normal normal = normalFormDto.createNormal();
            /*ormal.setCategory("부산");*/
            normal.setLike_count(0L);
            normal.setView_count(0L);
            normalBoardRepository.save(normal);
        } catch (DataAccessException e) {
            // 데이터베이스 예외인 경우 처리
            // 예외 메시지를 로그에 남기거나 다른 처리를 추가할 수 있습니다.
            e.printStackTrace();
            // 예외를 다시 던지지 않고 여기서 처리하도록 합니다.
        } catch (Exception e) {
            // 다른 예외인 경우 처리
            // 예외 메시지를 로그에 남기거나 다른 처리를 추가할 수 있습니다.
            e.printStackTrace();
            // 예외를 다시 던지도록 합니다.
            throw e;
        }
    }
    public NormalFormDto boardDetail(Long boardId) {
        Normal normal = normalBoardRepository.findById(boardId).orElseThrow((EntityNotFoundException::new));
        normal.viewCount();
        NormalFormDto normalFormDto = NormalFormDto.of(normal);



        return normalFormDto;
    }
    public NormalFormDto updateBoard(NormalFormDto normalFormDto) {
        Normal normal = normalBoardRepository.findById(normalFormDto.getNormalId()).orElseThrow((EntityNotFoundException::new));
        normal.updateEntity(normalFormDto);
        normalFormDto = NormalFormDto.of(normal);

        return normalFormDto;
    }
    //게시글 삭제
    public void deleteBoard(Long normalId) {normalBoardRepository.deleteById(normalId);
    }

    public Long like_count(Long data, Long normalId) {
        Normal normal = normalBoardRepository.findById(normalId).orElseThrow((EntityNotFoundException::new));
        Long like_count = normal.likeCount(data);

        return like_count;
    }
}