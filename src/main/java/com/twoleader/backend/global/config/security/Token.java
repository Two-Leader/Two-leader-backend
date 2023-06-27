package com.twoleader.backend.global.config.security;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
  private String grantType;
  private String accessToken;
  private String refreshToken;
}
