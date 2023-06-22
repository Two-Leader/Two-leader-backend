package com.twoleader.backend.global.result.WebSocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OutputMessage<T> {
    private OutputMessageCode code;
    private T data;
}