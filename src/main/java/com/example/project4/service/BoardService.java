package com.example.project4.service;

import com.example.project4.dto.NotificationFormDto;
import com.example.project4.entity.Notification;
import com.example.project4.repository.LikeInfoRepository;
import com.example.project4.repository.Notice_boardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    @Autowired
    private Notice_boardRepository boardRepository;
    @Autowired
    private LikeInfoRepository likeInfoRepository;

    @Transactional
    public void saveBoard(NotificationFormDto notificationFormDto) {
        System.out.println(notificationFormDto.getTitle());
        Notification notification = notificationFormDto.createNotification();
        notification.setLike_count(0L);
        notification.setView_count(0L);
        boardRepository.save(notification);
    }

    public NotificationFormDto notificationDetail(Long notificationId) {
        Notification notification = boardRepository.findById(notificationId).orElseThrow((EntityNotFoundException::new));
        notification.viewCount();
        NotificationFormDto notificationFormDto = NotificationFormDto.of(notification);

        return notificationFormDto;
    }

    public void deleteBoard(Long notificationId) {
        boardRepository.deleteById(notificationId);
    }

    public void updateBoard(NotificationFormDto notificationFormDto) {
        Notification notification = boardRepository.findById(notificationFormDto.getNotificationId()).orElseThrow((EntityNotFoundException::new));
        notification.updateEntity(notificationFormDto);
    }

    public Long like_count(Long data, Long notificationId) {
        Notification notification = boardRepository.findById(notificationId).orElseThrow((EntityNotFoundException::new));
        Long like_count = notification.likeCount(data);

        return like_count;
    }

    public boolean isLike(Long member_id, Long board_id) {
        Boolean result = likeInfoRepository.findByMemberIdAndBoardId(member_id, board_id);

        return likeInfoRepository.findByMemberIdAndBoardId(member_id, board_id);
    }
}
