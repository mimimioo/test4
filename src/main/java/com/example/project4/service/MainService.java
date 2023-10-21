package com.example.project4.service;

import com.example.project4.dto.NotificationFormDto;
import com.example.project4.entity.Notification;
import com.example.project4.repository.Notice_boardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MainService {
    @Autowired
    Notice_boardRepository noticeBoardRepository;
    @Transactional(readOnly = true)
    public Page<NotificationFormDto> getMainNoticePage(Pageable pageable){
        ModelMapper modelMapper = new ModelMapper();
        Page<Notification> notifications = noticeBoardRepository.findByLike_count(pageable);
        Page<NotificationFormDto> notificationsDto = notifications.map(notification -> modelMapper.map(notification, NotificationFormDto.class));

        return notificationsDto;
    }
}