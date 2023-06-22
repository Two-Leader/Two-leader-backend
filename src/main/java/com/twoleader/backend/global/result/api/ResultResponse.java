package com.twoleader.backend.global.result.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class ResultResponse<T> {

  private final String message;
  private Object data;

  @JsonCreator
  public ResultResponse(ResultCode resultCode) {
    this.message = resultCode.getMessage();
  }

  @JsonCreator
  public ResultResponse(ResultCode resultCode, Object data) {
    this.message = resultCode.getMessage();
    this.data = data;
  }
}
