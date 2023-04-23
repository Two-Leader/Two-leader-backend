package com.twoleader.backend.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class GetUserResponse {
    private UUID user_uuid;
    private String user_name;
}
