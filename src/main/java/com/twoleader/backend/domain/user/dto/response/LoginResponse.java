package com.twoleader.backend.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class LoginResponse {
    private UUID userUuid;
    private String nickName;
}
