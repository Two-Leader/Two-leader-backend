package com.twoleader.backend.domain.studyRoom.mapper;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudyRoomMapper {
    public StudyRoom toEntity(CreateStudyRoomRequest request) {
        return StudyRoom.builder().roomName(request.getRoomName()).build();
    }

    public GetStudyRoomResponse toDto(StudyRoom studyRoom) {
        return GetStudyRoomResponse.builder()
                .roomUuid(studyRoom.getRoomUuid())
                .roomName(studyRoom.getRoomName())
                .build();
    }
    public List<GetStudyRoomResponse> toDto(List<StudyRoom> studyRooms) {
        return studyRooms.stream().map(this::toDto).collect(Collectors.toList());
    }
}
