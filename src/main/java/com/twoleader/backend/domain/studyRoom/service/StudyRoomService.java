package com.twoleader.backend.domain.studyRoom.service;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomDto;
import com.twoleader.backend.domain.studyRoom.dto.response.FindStudyRoomDto;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.mapper.StudyRoomMapper;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class StudyRoomService {
    private final StudyRoomRepository studyRoomRepository;
    private final StudyRoomMapper studyRoomMapper;

    public StudyRoom createStudyRoom(CreateStudyRoomDto createStudyRoomDto) {
        StudyRoom studyRoom = StudyRoom.builder()
                .room_name(createStudyRoomDto.getRoom_name())
                .build();
        return studyRoomRepository.save(studyRoom);
    }

    public List<FindStudyRoomDto> findAllStudyRoom(){
        List<StudyRoom> rooms = studyRoomRepository.findAll();
        return studyRoomMapper.toDto(rooms);
    }
}
