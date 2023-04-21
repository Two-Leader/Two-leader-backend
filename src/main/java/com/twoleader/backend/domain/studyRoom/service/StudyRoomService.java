package com.twoleader.backend.domain.studyRoom.service;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomDto;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class StudyRoomService {
    private final StudyRoomRepository studyRoomRepository;

    public StudyRoom createStudyRoom(CreateStudyRoomDto createStudyRoomDto) {
        StudyRoom studyRoom = StudyRoom.builder()
                .room_name(createStudyRoomDto.getRoomName())
                .build();

        return studyRoomRepository.save(studyRoom);
    }

    public List<StudyRoom> findAllStudyRoom(){
        return studyRoomRepository.findAll();
    }
}
