package com.twoleader.backend.domain.chat.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.twoleader.backend.domain.chat.controller.WebSocketController;
import com.twoleader.backend.domain.chat.document.Chat;
import com.twoleader.backend.domain.chat.dto.response.ChatMessage;
import com.twoleader.backend.domain.chat.dto.response.GetChatResponse;
import com.twoleader.backend.global.result.api.ResultCode;
import com.twoleader.backend.global.result.api.ResultResponse;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {
  public Chat toEntity(UUID roomUuid, ChatMessage request) {
    return Chat.builder()
        .roomUuid(roomUuid)
        .userId(request.getUserId())
        .message(request.getMessage())
        .build();
  }

  public GetChatResponse toDto(Chat chat) {
    return GetChatResponse.builder()
        .userId(chat.getUserId())
        .message(chat.getMessage())
        .time(chat.getCreatedAt())
        .build();
  }

  public Page<GetChatResponse> toDto(Page<Chat> chat) {
    return chat.map(this::toDto);
  }

  public EntityModel toModel(
      ResultCode code, Page<GetChatResponse> response, UUID roomUuid, int page) {
    EntityModel<ResultResponse<Object>> entity =
        EntityModel.of(
            new ResultResponse<>(code, response),
            linkTo(methodOn(WebSocketController.class).getChat(roomUuid, page)).withSelfRel());
    if (response.hasNext())
      entity.add(
          linkTo(methodOn(WebSocketController.class).getChat(roomUuid, page + 1)).withRel("next"));

    return entity;
  }
}
