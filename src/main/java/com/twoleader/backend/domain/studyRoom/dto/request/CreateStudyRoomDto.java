package com.twoleader.backend.domain.studyRoom.dto.request;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudyRoomDto {

    @NotBlank
    private String room_name;

    public StudyRoom toEntity(){
        return StudyRoom.builder()
                .room_name(this.room_name)
                .build();
    }
}
