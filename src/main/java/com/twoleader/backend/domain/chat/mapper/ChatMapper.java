package com.twoleader.backend.domain.chat.mapper;

import com.twoleader.backend.domain.chat.dto.request.ChatRequest;
import com.twoleader.backend.domain.chat.document.Chat;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChatMapper {
    public Chat toEntity(UUID roomUuid, ChatRequest request){
        return Chat.builder().roomUuid(roomUuid).userId(request.getUserId()).message(request.getMessage()).build();
    }
}
