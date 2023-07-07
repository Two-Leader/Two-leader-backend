package com.twoleader.backend.domain.chat.dto.request;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChatRequest {
  private Long userId;
  private String message;
}
