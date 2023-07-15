package com.twoleader.backend.domain.chat.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChatMessage implements Serializable {
  private UUID roomUuid;
  private Long userId;
  private String message;
}
