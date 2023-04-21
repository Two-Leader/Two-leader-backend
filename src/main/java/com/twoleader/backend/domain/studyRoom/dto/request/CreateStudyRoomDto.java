package com.twoleader.backend.domain.studyRoom.dto.request;

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
    private String roomName;
}
