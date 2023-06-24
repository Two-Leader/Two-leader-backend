package com.twoleader.backend.domain.user.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
