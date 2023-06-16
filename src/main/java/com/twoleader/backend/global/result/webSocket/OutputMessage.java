package com.twoleader.backend.global.result.webSocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OutputMessage<T> {
    private OutputMessageCode code;
    private T data;
}
