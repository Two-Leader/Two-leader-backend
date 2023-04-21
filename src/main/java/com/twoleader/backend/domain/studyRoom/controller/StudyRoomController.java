package com.twoleader.backend.domain.studyRoom.controller;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomDto;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/study")
@RestController
public class StudyRoomController {

    private final StudyRoomService studyRoomService;

    @PostMapping("")
    public void createStudyRoom( @RequestBody CreateStudyRoomDto studyRoomDto) {
        StudyRoom studyRoom = studyRoomService.createStudyRoom(studyRoomDto);
        log.info("CREATE Chat Room [{}]", studyRoom.toString());
    }

    @GetMapping("")
    public List<StudyRoom> getAllStudyRoom(){
        List<StudyRoom> studyRooms = studyRoomService.findAllStudyRoom();
        log.info("getAllStudyRoom [{}]",studyRooms.toString());
        return studyRooms;
    }
}
