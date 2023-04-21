package com.twoleader.backend.domain.studyRoom.mapper;

import com.twoleader.backend.domain.studyRoom.dto.response.FindStudyRoomDto;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudyRoomMapper {
    public FindStudyRoomDto toDto(StudyRoom studyRoom){
        return FindStudyRoomDto.builder()
                .room_uuid(studyRoom.getRoom_uuid())
                .room_name(studyRoom.getRoom_name())
                .build();
    }

    public List<FindStudyRoomDto> toDto(List<StudyRoom> studyRooms){
        return studyRooms.stream().map(this::toDto).collect(Collectors.toList());
    }
}
