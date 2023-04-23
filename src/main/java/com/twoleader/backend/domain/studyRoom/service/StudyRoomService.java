package com.twoleader.backend.domain.studyRoom.service;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.exception.NotFoundStudyRoom;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import com.twoleader.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudyRoomService {
    private final StudyRoomRepository studyRoomRepository;
    private final UserRepository userRepository;

    public StudyRoom createStudyRoom(CreateStudyRoomRequest request) {
        StudyRoom studyRoom = request.toEntity();
        return studyRoomRepository.save(studyRoom);
    }

    public List<GetStudyRoomResponse> findAllStudyRoom(){
        List<StudyRoom> rooms = studyRoomRepository.findAll();
        return rooms.stream().map(StudyRoom::toDto).collect(Collectors.toList());
    }

    public StudyRoom findStudyRoomById(Long id){
        return studyRoomRepository.findById(id).orElseThrow(NotFoundStudyRoom::new);
    }
}
