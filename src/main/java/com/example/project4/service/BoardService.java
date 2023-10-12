package com.example.project4.service;

import com.example.project4.dto.NotificationFormDto;
import com.example.project4.entity.Notification;
import com.example.project4.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.MemberSubstitution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public Long saveBoard(NotificationFormDto notificationFormDto) {
        Notification notification =notificationFormDto.createNotification();
        boardRepository.save(notification);

        return notification.getNotificationId();
    }

    @Transactional(readOnly = true)
    public NotificationFormDto notificationDetail(Long notificationId) {
        Notification notification = boardRepository.findById(notificationId).orElseThrow((EntityNotFoundException::new));
        notification.viewCount();
        NotificationFormDto notificationFormDto = NotificationFormDto.of(notification);

        return notificationFormDto;
    }

    public void deleteBoard(Long notificationId) {
        boardRepository.deleteById(notificationId);
    }

    public Long updateBoard(NotificationFormDto notificationFormDto) {
        Notification notification = boardRepository.findById(notificationFormDto.getNotificationId()).orElseThrow((EntityNotFoundException::new));
        notification.updateEntity(notificationFormDto);
        boardRepository.save(notification);

        return notification.getNotificationId();
    }

    public void like_count(Long notificationId, Long like) {
        Notification notification = boardRepository.findById(notificationId).orElseThrow((EntityNotFoundException::new));
        Long like_count = notification.likeCount(like);
        boardRepository.save(notification);
    }
}
