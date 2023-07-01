package com.twoleader.backend.domain.studyRoom.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckStudyRoomPasswordRequest {
    @NotBlank
    private String password;
}
